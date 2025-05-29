public class PaymentService {

    public boolean processPayment(Order order, double paymentAmount) {
        if (order.isPaid()) return false;
        if (paymentAmount >= order.getAmount()) {
            order.markPaid();
            return true;
        }
        return false;
    }
}
