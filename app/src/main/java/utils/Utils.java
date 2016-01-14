package utils;
import java.text.DecimalFormat;

/**
 * Created by Atul Kumar on 12-12-2015.
 */

public class Utils {


    public static String formatNumber( int value ) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(value);

        return formatted;
    }
}
