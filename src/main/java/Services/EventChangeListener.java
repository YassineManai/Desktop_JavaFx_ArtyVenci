package services;

public interface EventChangeListener<T> {
    void onSupprimerClicked();
    void onModifierClicked(T t);
}
