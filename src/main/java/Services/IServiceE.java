package services;

import java.util.List;

public interface IServiceE<T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer(T t);
    List<T> afficher();
}
