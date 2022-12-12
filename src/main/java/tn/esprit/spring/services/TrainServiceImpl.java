package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.TrainRepository;
import tn.esprit.spring.repository.VoyageRepository;
import tn.esprit.spring.repository.VoyageurRepository;

import java.util.ArrayList;
import java.util.List;

@Service

public class TrainServiceImpl implements ITrainService {



    @Autowired
    VoyageurRepository voyageurRepository;


    @Autowired
    TrainRepository trainRepository;

    @Autowired
    VoyageRepository voyageRepository;


    public void ajouterTrain(Train t) {

        trainRepository.save(t);
    }
    @Override
    public int trainPlacesLibres(Ville nomGareDepart) {
        int cpt = 0;
        int occ = 0;
        List<Voyage> listvoyage = (List<Voyage>) voyageRepository.findAll();


        for (int i = 0; i < listvoyage.size(); i++) {
            if (listvoyage.get(i).getGareDepart() == nomGareDepart) {
                cpt = cpt + listvoyage.get(i).getTrain().getNbPlaceLibre();
                occ = occ + 1;
            }
        }
        if(occ!=0){
            return cpt / occ;
        }
        return 0;

    }

@Override
    public List<Train> listerTrainIndirecte(Ville nomGareDepart, Ville nomGareArrivee) {

        List<Train> lestrainsRes = new ArrayList<>();
        List<Voyage> lesvoyage ;
        lesvoyage = (List<Voyage>) voyageRepository.findAll();
        for (int i = 0; i < lesvoyage.size(); i++) {
            if (lesvoyage.get(i).getGareDepart() == nomGareDepart) {
                for (int j = 0; j < lesvoyage.size(); j++) {
                    if (lesvoyage.get(i).getGareArrivee() == lesvoyage.get(j).getGareDepart() && lesvoyage.get(j).getGareArrivee() == nomGareArrivee) {
                        lestrainsRes.add(lesvoyage.get(i).getTrain());
                        lestrainsRes.add(lesvoyage.get(j).getTrain());

                    }

                }
            }
        }


        return lestrainsRes;
        //
    }


    @Transactional
    public void affecterTainAVoyageur(Long idVoyageur, Ville nomGareDepart, Ville nomGareArrivee, double heureDepart) {



        Voyageur c = voyageurRepository.findById(idVoyageur).orElse(null);
        List<Voyage> lesvoyages ;
        lesvoyages = voyageRepository.rechercheVoyage(nomGareDepart, nomGareDepart, heureDepart);

        for (int i = 0; i < lesvoyages.size(); i++) {
            if (lesvoyages.get(i).getTrain().getNbPlaceLibre() != 0) {
                lesvoyages.get(i).getMesVoyageurs().add(c);
                lesvoyages.get(i).getTrain().setNbPlaceLibre(lesvoyages.get(i).getTrain().getNbPlaceLibre() - 1);
            }
            voyageRepository.save(lesvoyages.get(i));
        }
    }

    @Override
    public void desaffecterVoyageursTrain(Ville nomGareDepart, Ville nomGareArrivee, double heureDepart) {
        List<Voyage> lesvoyages;
        lesvoyages = voyageRepository.rechercheVoyage(nomGareDepart, nomGareArrivee, heureDepart);


        for (int i = 0; i < lesvoyages.size(); i++) {
            for (int j = 0; j < lesvoyages.get(i).getMesVoyageurs().size(); j++) {
                lesvoyages.get(i).getMesVoyageurs().remove(j);
            }
            lesvoyages.get(i).getTrain().setNbPlaceLibre(lesvoyages.get(i).getTrain().getNbPlaceLibre() + 1);
            lesvoyages.get(i).getTrain().setEtat(etatTrain.PREVU);
            voyageRepository.save(lesvoyages.get(i));
            trainRepository.save(lesvoyages.get(i).getTrain());
        }
    }



}

    
