package py.una.pol.tesis.pdi;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author Arturo
 */
public class Imagen {

    File imageFile;
    double contraste;

    /**
     * Instancia una imagen a partir del path
     *
     * @param imagePath: Path de la imagen
     * @throws IOException: Si el path no corresponde a un archivo de tipo
     * image/
     */
    public Imagen(String imagePath) throws IOException {
        this(new File(imagePath), null);
    }

    /**
     * Instancia una imagen a partir del path
     *
     * @param imagePath: Path de la imagen
     * @param otherMimetypes: Lista de Mimetypes que desea soportar ademas del
     * tipo image/
     * @throws IOException: Si el path no corresponde a un archivo de tipo image
     * o a uno de los indicados en otherMimetypes
     */
    public Imagen(String imagePath, List<String> otherMimetypes) throws IOException {
        this(new File(imagePath), otherMimetypes);
    }

    /**
     * Almacena una instancia de la imagen enviada como parametro
     *
     * @param imageFile: imagen
     * @throws IOException: Si el archivo no es de tipo image/
     */
    public Imagen(File imageFile) throws IOException {
        this(imageFile, null);
    }

    /**
     * Almacena una instancia de la imagen enviada como parametro
     *
     * @param imageFile: imagen
     * @param otherMimetypes: Lista de Mimetypes que desea soportar ademas del
     * tipo image/
     * @throws IOException: Si el archivo no es de tipo image o uno de los
     * indicados en otherMimetypes
     */
    public Imagen(File imageFile, List<String> otherMimetypes) throws IOException {
        if (this.esImagen(imageFile, otherMimetypes)) {
            this.imageFile = imageFile;
            this.contraste = this.calcularContraste();
        } else {
            StringBuilder s = new StringBuilder();
            s.append("El archivo no corresponde a una imagen o a uno de los tipos indicados en otherTypes. Name->[");
            s.append(imageFile.getName());
            s.append("]. Type->[");
            s.append(new MimetypesFileTypeMap().getContentType(imageFile));
            s.append("].");
            throw new IOException(s.toString());
        }
    }

    private boolean esImagen(File imageFile, List<String> otherMimetypes) {
        String mimetype = new MimetypesFileTypeMap().getContentType(imageFile);
        String type = mimetype.split("/")[0];
        return type.equals("image") || (otherMimetypes != null && otherMimetypes.contains(mimetype));
    }

    public Imagen mejorarImagen(ElementoEstructurante ee) {
        //aplicar metodo de mejora de imagen
        return this;
    }

    public double getContraste() {
        return contraste;
    }

    private double calcularContraste() {
        return 1;//calcular contraste aqui
    }

    public double calcularSSIM(Imagen imagen) {
        return 1;//calcular ssim aqui
    }
}
