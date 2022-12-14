package tn.esprit.spring.services;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;
import java.util.List;

public interface ITrainService {
     void ajouterTrain(Train t);
     void affecterTainAVoyageur(Long   idVoyageur, Ville nomGareDepart, Ville nomGareArrivee,  double heureDepart);
     int trainPlacesLibres(Ville nomGareDepart);
     List<Train> listerTrainIndirecte(Ville nomGareDepart, Ville nomGareArrivee);
     void desaffecterVoyageursTrain(Ville nomGareDepart, Ville nomGareArrivee, double heureDepart);

}
