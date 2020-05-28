package company.model.money;

import company.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "third_booking", schema = "third")
@AllArgsConstructor
@NoArgsConstructor
public class Money extends BaseEntity {
    @Column(name = "money")
    private Integer money;
}
