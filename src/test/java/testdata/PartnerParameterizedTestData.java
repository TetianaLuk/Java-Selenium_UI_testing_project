package testdata;

import models.Partner;
import org.instancio.Instancio;

import java.util.Arrays;
import java.util.List;

public class PartnerParameterizedTestData {

    public static List<Partner> getPartnerParameterizedTestData() {
        Partner user1 = Instancio.create(Partner.getPartnerModel());
        Partner user2 = Instancio.create(Partner.getPartnerModel());
        Partner user3 = Instancio.create(Partner.getPartnerModel());
        Partner user4 = Instancio.create(Partner.getPartnerModel());

        return Arrays.asList(user1, user2, user3, user4);
    }

}
