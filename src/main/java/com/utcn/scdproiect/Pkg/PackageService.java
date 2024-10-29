package com.utcn.scdproiect.Pkg;



import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Transactional
    public Package createPackage(Package pack) {
        return packageRepository.save(pack);
    }

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }



    //TO DO: add UPDATE and DELETE


}