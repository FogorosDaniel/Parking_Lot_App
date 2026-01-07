package org.example.parkinglot.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "car_photos")
public class CarPhoto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String fileType;
    private byte[] fileContent;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public byte[] getFileContent() { return fileContent; }
    public void setFileContent(byte[] fileContent) { this.fileContent = fileContent; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
}