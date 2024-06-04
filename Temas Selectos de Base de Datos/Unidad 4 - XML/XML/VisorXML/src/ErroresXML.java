import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;

public class ErroresXML implements ErrorHandler {
    private String errores;

    public ErroresXML() {
        errores = "";
    }

    public String getErrores() {
        return errores;
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        errores += "Warning: " + exception.getMessage() + "\n";
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        errores += "Error: " + exception.getMessage() + "\n";
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        errores += "Fatal error: " + exception.getMessage() + "\n";
    }
}