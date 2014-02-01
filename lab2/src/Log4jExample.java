import org.apache.log4j.Logger;

/**
 * Created by nikos on 1/31/14.
 */
public class Log4jExample {
    static Logger logger = Logger.getLogger(Log4jExample.class);

    public static void main(String[] argv){
        logger.info("Start of setUp");
        logger.debug("Blah");
        if (1==1) {
            logger.warn("1==1!");
        }

    }
}
