package org.example.bll;

import org.example.dal.PromotionDal;
import org.example.dto.PromotionDto;

import java.util.List;

public class PromotionManager {
    private final PromotionDal promotionDal;

    public PromotionManager() {
        this.promotionDal = new PromotionDal();
    }

    public boolean addPromotion(PromotionDto promotion) {
        return promotionDal.addPromotion(promotion);
    }

    public PromotionDto getPromotionByCode(String code) {
        return promotionDal.getPromotionByCode(code);
    }

    public List<PromotionDto> getAllPromotions() {
        return promotionDal.getAllPromotions();
    }

    public boolean updatePromotion(PromotionDto promotion) {
        return promotionDal.updatePromotion(promotion);
    }

    public boolean deletePromotion(String code) {
        return promotionDal.deletePromotion(code);
    }
}