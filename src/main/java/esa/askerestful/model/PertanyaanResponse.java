package esa.askerestful.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PertanyaanResponse {


    private String id;

    private String header;

    private String deskripsi;

    private Integer suka;

    private Timestamp tanggal;

}
