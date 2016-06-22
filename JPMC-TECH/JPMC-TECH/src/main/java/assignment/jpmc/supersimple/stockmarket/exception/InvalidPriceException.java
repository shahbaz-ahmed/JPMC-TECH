package assignment.jpmc.supersimple.stockmarket.exception;

public class InvalidPriceException
    extends Exception
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -5374829250918566777L;

    public InvalidPriceException( String message )
    {
        super( message );
    }
}
