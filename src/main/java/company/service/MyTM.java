package company.service;

import company.DefaultTransactorManager;
import company.model.TripBooking;
import company.model.fly.FlyBooking;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.SQLException;

import static javax.transaction.xa.XAResource.TMNOFLAGS;
import static javax.transaction.xa.XAResource.TMSUCCESS;

@Service
@RequiredArgsConstructor
public class MyTM {
    public static byte[] gtrid = new byte[]{0x44, 0x11, 0x55, 0x66};
    public static byte[] bqual = new byte[]{0x00, 0x22, 0x00};
    private final DataSource moneyDataSource;
    private final DataSource hotelDataSource;
    private final DataSource flyDataSource;

    @EqualsAndHashCode
    @AllArgsConstructor
    public static class XID implements Xid {

        private Integer formatId;
        private byte[] globalTxId;
        private byte[] branchQualifier;

        @Override
        public int getFormatId() {
            return formatId;
        }

        @Override
        public byte[] getGlobalTransactionId() {
            return globalTxId;
        }

        @Override
        public byte[] getBranchQualifier() {
            return branchQualifier;
        }
    }

    public void twoPhaseCommitTransaction(TripBooking tripBooking) {

        var xid = new DefaultTransactorManager.XID(100, gtrid, bqual);

        try {

            final var flight = tripBooking.getFlight();
            var insertFlyQuery = String.format("INSERT INTO fly.fly_booking" +
                    "(client_name, fly_number, arrival_place, departure_place, arrival_date) " +
                    "VALUES ('%s', '%s', '%s', '%s', '%s')", flight.getClientName(), flight.getFlyNumber(), flight.getFrom(), flight.getTo(), flight.getDate());

            var xaFlyResource = prepareTransaction(((AtomikosDataSourceBean)flyDataSource).getXaDataSource().getXAConnection(), xid, insertFlyQuery);

            final var hotel = tripBooking.getHotel();
            var insertHotelQuery = String.format("INSERT INTO hotel.hotel_booking" +
                    "(client_name, hotel_name, arrival_date, departure_date) " +
                    "VALUES ('%s', '%s', '%s', '%s')", hotel.getClientName(), hotel.getHotelName(), hotel.getFrom(), hotel.getTo());

            var xaHotelResource = prepareTransaction(((AtomikosDataSourceBean)hotelDataSource).getXaDataSource().getXAConnection(), xid, insertHotelQuery);

            commitTwoPhase(xaFlyResource, xaHotelResource, xid);

        } catch (SQLException sqe) {
            System.out.println("SQLException caught: " + sqe.getMessage());
            sqe.printStackTrace();
        } catch (XAException xae) {
            System.out.println("XA error is " + xae.getMessage());
            xae.printStackTrace();
        }


    }

    private static XAResource prepareTransaction(XAConnection xaConnection,
                                                 Xid xid, String query) throws XAException, SQLException {
        var xaResource = xaConnection.getXAResource();

        xaResource.start(xid, TMNOFLAGS);
        executeQuery(xaConnection, query);
        xaResource.end(xid, TMSUCCESS);

        return xaResource;
    }

    private static void executeQuery(XAConnection xaConnection, String query) throws SQLException {
        var jdbcConnection = xaConnection.getConnection();
        var statement = jdbcConnection.createStatement();
        statement.execute(query);
    }

    private static void commitTwoPhase(XAResource xaRes1, XAResource xaRes2, DefaultTransactorManager.XID xid) {
        try {
            xaRes1.prepare(xid);
            xaRes2.prepare(xid);

            xaRes1.commit(xid, false);
            xaRes2.commit(xid, false);
        } catch (XAException e) {
            try {
                xaRes1.rollback(xid);
                xaRes2.rollback(xid);
            } catch (XAException ex) {
                ex.printStackTrace();
            }
        }

    }
}
