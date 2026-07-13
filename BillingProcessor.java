import java.util.UUID;

public class BillingProcessor {
    
    private static final double APP_STORE_CUT_PERCENT = 0.20; 
    private static final double GIVE_BACK_PERCENT = 0.50;    

    public static class TransactionResult {
        public String transactionId;
        public double totalCharged;
        public double storeFee;
        public double dedicatedGiveBack;
        public double operationalReinvestment;

        @Override
        public String toString() {
            return String.format(
                "--- TRANSACTION LOG ---\n" +
                "Gross Revenue: $%.2f\n" +
                "Community Give-Back (50%%): $%.2f\n" +
                "-----------------------",
                totalCharged, dedicatedGiveBack
            );
        }
    }

    public TransactionResult purchaseAssetBundle(String itemSku, double basePrice) {
        TransactionResult result = new TransactionResult();
        result.transactionId = UUID.randomUUID().toString();
        result.totalCharged = basePrice;
        result.storeFee = basePrice * APP_STORE_CUT_PERCENT;
        result.dedicatedGiveBack = basePrice * GIVE_BACK_PERCENT;
        result.operationalReinvestment = basePrice * (1.0 - (APP_STORE_CUT_PERCENT + GIVE_BACK_PERCENT));

        // Route funds to your Mutual Aid escrow wallet
        System.out.printf("[DISTRIBUTION] Securing $%.2f to Mutual Aid wallet.\n", result.dedicatedGiveBack);

        return result;
    }

    public static void main(String[] args) {
        BillingProcessor storeCore = new BillingProcessor();
        TransactionResult invoice = storeCore.purchaseAssetBundle("SKU_LAUNCH_BUNDLE", 9.99);
        System.out.println(invoice);
    }
}
