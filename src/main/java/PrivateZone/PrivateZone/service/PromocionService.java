package PrivateZone.PrivateZone.service;

import PrivateZone.PrivateZone.domain.Promocion;
import java.util.List;

public interface PromocionService {

    /** Promociones activas, en el orden en que se muestran en el carrusel. */
    List<Promocion> getPromocionesActivas();
}