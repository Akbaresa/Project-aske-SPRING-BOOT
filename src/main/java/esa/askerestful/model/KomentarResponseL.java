package esa.askerestful.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class KomentarResponseL {
    private String idKomentar;
    private String nama;
    private String deskripsi;
    private String waktu;
}
