package net.devstudy;

public class Constants {
    public static final String CURRENT_SHOPPING_CART = "CURRENT_SHOPPING_CART";

    public enum Cookie {
        SHOPPING_CART("iSCC", 60 * 60 * 24 *365);

      private final String name;
      private final int ttl;

        Cookie(String name, int ttl) {
            this.name = name;
            this.ttl = ttl;
        }

        public String getName() {
            return name;
        }

        public int getTtl() {
            return ttl;
        }
    }
}
