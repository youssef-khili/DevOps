package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Voyageur;
import tn.esprit.spring.repository.VoyageurRepository;

import java.util.List;


@Service
public class VoyageurServiceImpl implements IVoyageurService{

	@Autowired
	VoyageurRepository voyageurRepository;


	public void ajouterVoyageur(Voyageur voyageur) {
		voyageurRepository.save(voyageur);
		
    }

	@Override
	public void modifierVoyageur(Voyageur voyageur) {
		voyageurRepository.save(voyageur);
	}

	@Override
	public List<Voyageur> recupererAll() {

		return (List<Voyageur>) voyageurRepository.findAll();
	}

	@Override
	public Voyageur recupererVoyageParId(long idVoyageur) {

		return voyageurRepository.findById(idVoyageur).orElse(null);
	}

	@Override
	public void supprimerVoyageur(Voyageur v) {
		voyageurRepository.delete(v);
	}

}
