package by.teachmeskills.eshop.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "category_images")
public class CategoryImage extends BaseEntity {

    @Column(name = "IMAGE")
    private String imagePath;

    @OneToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @Override
    public String toString() {
        return imagePath;
    }
}