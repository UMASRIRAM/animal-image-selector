
package org.test.camunda.AnimalImageSelector.model;


import jakarta.persistence.*;



@Entity
@Table(name = "images")
public class AnimalImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "processId")
    private long processId;

    @Column(name = "categoryName")
    private String categoryName;

    @Lob
    @Column(name = "categoryPhoto")
    private byte[] categoryPhoto;

    public AnimalImage() {}

    public AnimalImage(long processId, String categoryName, byte[] categoryPhoto) {
        this.processId = processId;
        this.categoryName = categoryName;
        this.categoryPhoto = categoryPhoto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProcessId() {
        return processId;
    }

    public void setProcessId(long processId) {
        this.processId = processId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public byte[] getCategoryPhoto() {
        return categoryPhoto;
    }

    public void setCategoryPhoto(byte[] categoryPhoto) {
        this.categoryPhoto = categoryPhoto;
    }
}