package fast_fix.service.interfaces;

import fast_fix.domain.entity.User;

public interface EmailService {

    void sendConfirmationEmail(User user);
    void sendInsuranceChangedInfoEmail(User user);
    void sendFuelParamInfoEmail(User user);
    void sendMaintenanceDateChangedEmail(User user);
    void sendDeleteAccountInfoEmail(User user);
    void sendMaintenanceReminderEmail(User user);

}