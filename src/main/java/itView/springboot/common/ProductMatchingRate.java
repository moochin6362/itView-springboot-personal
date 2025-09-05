package itView.springboot.common;

import java.time.LocalDate;
import java.time.Period;

import itView.springboot.vo.Product;
import itView.springboot.vo.User;

public class ProductMatchingRate {
	
	private static final int SKIN_TYPE_WEIGHT = 20;
	private static final int SKIN_TROUBLE_WEIGHT = 20;
    private static final int AGE_WEIGHT = 3;
    private static final int GENDER_WEIGHT = 7;
    private static final int PRICE_WEIGHT = 15;
    private static final int INGREDIENT_WEIGHT = 15;
    private static final int ECO_FRIENDLY_WEIGHT = 10;
    private static final int PERSONAL_COLOR_WEIGHT = 5;
    private static final int HEAD_SKIN_WEIGHT = 5;
	
	// 매칭률 계산
	public double calculateMatchRate(User u, Product p) {
        int score = 0;
        int total = 0;

        // 피부타입 // 공용이면 +
        if (p.getSkinType().equals("공용")) {
	        total += SKIN_TYPE_WEIGHT;
	        if (u.getSkinType().equals(p.getSkinType())) {
	            score += SKIN_TYPE_WEIGHT;
	        }
        }
        
        // 피부고민 // 해당없음이면 -
        total += SKIN_TROUBLE_WEIGHT;
        if (!p.getSkinTrouble().equals("해당 없음")) {
            if (u.getSkinTrouble().equals(p.getSkinTrouble())) {
                score += SKIN_TROUBLE_WEIGHT;
            }
        }

        // 나이
        int userAge = Period.between(u.getUserAge(), LocalDate.now()).getYears();
        total += AGE_WEIGHT;
        score += matchAge(userAge, p.getUserAge());

        // 성별 // 공용이면 +
        if (p.getGender().equals("A")) {
	        total += GENDER_WEIGHT;
	        if (u.getUserGender().equals(p.getGender())) {
	            score += GENDER_WEIGHT;
	        }
        }

        // 선택 항목 -------------------------
        // 희망 가격대
        if (u.getHopePrice() != null) {
            total += PRICE_WEIGHT;
            if (matchPrice(u.getHopePrice(), p.getProductPrice())) {
                score += PRICE_WEIGHT;
            }
        }

        // 성분
        if (u.getIngredient() != null && !u.getIngredient().isEmpty()) {
            total += INGREDIENT_WEIGHT;
            if (u.getIngredient().equals(p.getIngredient())) {
                score += INGREDIENT_WEIGHT;
            }
        }

        // 친환경 여부
        if (u.getEcoFriendly() != null && !u.getEcoFriendly().isEmpty()) {
            total += ECO_FRIENDLY_WEIGHT;
            if (u.getEcoFriendly().equals(p.getEcoFriendly())) {
                score += ECO_FRIENDLY_WEIGHT;
            }
        }

        // 퍼스널 컬러
        if (u.getPersonalColor() != null && !p.getPersonalColor().equals("해당 없음")) {
            total += PERSONAL_COLOR_WEIGHT;
            if (u.getPersonalColor().equals(p.getPersonalColor())) {
                score += PERSONAL_COLOR_WEIGHT;
            }
        }

        // 두피 고민
        if (u.getHeadSkin() != null && !p.getHeadSkin().equals("해당 없음")) {
            total += HEAD_SKIN_WEIGHT;
            if (u.getHeadSkin().equals(p.getHeadSkin())) {
                score += HEAD_SKIN_WEIGHT;
            }
        }
        
        // ✅ 최종 매칭률 (항상 0 ~ 100%)
        return ((double) score / total) * 100;
    }

	// 나이 매칭 (switch문)
    private int matchAge(int userAge, String productAge) {
        switch (productAge) {
        case "10대": return (userAge >= 0 && userAge <= 19) ? AGE_WEIGHT : 0;
        case "20대": return (userAge >= 20 && userAge <= 29) ? AGE_WEIGHT : 0;
        case "30대": return (userAge >= 30 && userAge <= 39) ? AGE_WEIGHT : 0;
        case "40대": return (userAge >= 40 && userAge <= 49) ? AGE_WEIGHT : 0;
        case "50대": return (userAge >= 50 && userAge <= 59) ? AGE_WEIGHT : 0;
        case "60대": return (userAge >= 60) ? AGE_WEIGHT : 0;
        case "모든 연령": return AGE_WEIGHT; // 모든 연령이면 +
        default: return 0;
        }
    }
	
    // 가격대 매칭 (버튼 값 기준)
    private boolean matchPrice(String hopePrice, int productPrice) {
        switch (hopePrice) {
            case "priceRange1": return productPrice >= 0 && productPrice < 10000;
            case "priceRange2": return productPrice >= 10000 && productPrice < 30000;
            case "priceRange3": return productPrice >= 30000 && productPrice < 50000;
            case "priceRange4": return productPrice >= 50000;
            default: return false;
        }
    }
	
}
