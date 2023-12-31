package esa.askerestful.service;

import esa.askerestful.entity.KredensialLokasi;
import esa.askerestful.entity.KredensialPendidikan;
import esa.askerestful.entity.User;
import esa.askerestful.model.CreateKredLokasiReq;
import esa.askerestful.model.KredLokasiResp;
import esa.askerestful.repository.LokasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class LokasiService {

    @Autowired
    public MicroService microService;

    @Autowired
    public LokasiRepository lokasiRepository;

    @Autowired
    public ValidationService validationService;

    public KredLokasiResp create(User user, CreateKredLokasiReq req){
        validationService.validate(user);
        KredensialLokasi kredensialLokasi = new KredensialLokasi();

        Optional<KredensialLokasi> kp = lokasiRepository
                .findByUser(user);

        if (kp.isEmpty()){
            kredensialLokasi.setIdKredensialLokasi(UUID.randomUUID().toString());
            kredensialLokasi.setLokasi(req.getLokasi());
            kredensialLokasi.setTahunMulai(req.getTahunMulai());
            kredensialLokasi.setTahunSelesai(req.getTahunSelesai());
            kredensialLokasi.setUser(user);
            lokasiRepository.save(kredensialLokasi);
        }else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "kredensial sudah dibuat");
        }


        return response(kredensialLokasi);
    }

    private KredLokasiResp response(KredensialLokasi req){
        return KredLokasiResp.builder()
                .idKredLokasi(req.getIdKredensialLokasi())
                .lokasi(req.getLokasi())
                .tahunMulai(req.getTahunMulai())
                .tahunSelesai(req.getTahunSelesai())
                .build();
    }

    @Transactional(readOnly = true)
    public KredLokasiResp get(String username){

        KredensialLokasi kredensialLokasi = lokasiRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "kredensial lokasi tidak ditemukan"
                ));

        return response(kredensialLokasi);
    }

    @Transactional
    public KredLokasiResp update(User user, CreateKredLokasiReq req, String idKredLokasi){
        validationService.validate(user);

        KredensialLokasi kredensialLokasi = lokasiRepository
                .findFirstByUserAndId(user, idKredLokasi)
                .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "kredensial lokasi tidak ditemukan"
                ));

        try {
            kredensialLokasi.setLokasi(req.getLokasi());
            kredensialLokasi.setTahunMulai(req.getTahunMulai());
            kredensialLokasi.setTahunSelesai(req.getTahunSelesai());
        }catch (Exception e){
            e.printStackTrace();
        }

        return response(kredensialLokasi);
    }

    @Transactional
    public void delete(User user, String idKredLokasi){
        validationService.validate(user);
        validationService.validate(idKredLokasi);

        KredensialLokasi kredensialLokasi = lokasiRepository
                .findFirstByUserAndId(user, idKredLokasi)
                .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "kredensial lokasi tidak ditemukan"
                ));

        lokasiRepository.delete(kredensialLokasi);
    }
}
