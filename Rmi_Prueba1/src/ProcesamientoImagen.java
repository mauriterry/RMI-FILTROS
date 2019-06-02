import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Maru
 */
public class ProcesamientoImagen extends UnicastRemoteObject implements Manejo_Imagenes{
    
    //para almacenar las imagene
    private ArrayList<byte[]> fotos = new ArrayList<byte[]>();
    int contador=0;
    
    private int colorRGBaSRGB(Color colorRGB){
        int colorSRGB;
        colorSRGB=(colorRGB.getRed() << 16) | (colorRGB.getGreen() << 8) | colorRGB.getBlue();
        return colorSRGB;
    }
    private BufferedImage clonarBufferedImage(BufferedImage bufferImage){
        BufferedImage copiaImagen=new BufferedImage (bufferImage.getWidth(),bufferImage.getHeight(),bufferImage.getType());
        copiaImagen.setData(bufferImage.getData());
        return copiaImagen;
    }
    private int calcularMediaColor(Color color){
        int averageColor;
        averageColor=(int)((color.getRed()+color.getGreen()+color.getBlue())/3);
        return averageColor;
    }
    private Color chequearUmbral(Color color, int umbral){
        Color colorSalida;
        if (this.calcularMediaColor(color)>=umbral){
            colorSalida=new Color(255,255,255, color.getAlpha());
        }else{
            colorSalida=new Color(0, 0, 0, color.getAlpha());
        }
        return colorSalida;
    }
        
    /*Abrir una imagen*/
    @Override
    public byte[] AbrirAImagen(byte[] img)throws RemoteException{
        try {
            try (FileOutputStream salida = new FileOutputStream("imagenOriginal.jpg")) {
                salida.write(img);   
                fotos.add(img);
                contador+=1;
            }
        } catch (IOException e) {
        }
        return img;
    }
        
    @Override
    public byte[] filtro2() throws RemoteException{
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color auxColor = new Color(biDestino.getRGB(i, j));
                    int mediaColor = this.calcularMediaColor(auxColor);
                      biDestino.setRGB(i, j,this.colorRGBaSRGB(new Color(mediaColor,mediaColor,mediaColor,auxColor.getAlpha())));                                                                 
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro2.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro6() throws RemoteException{
        byte[] bytesImg = new byte[1024*100];
        try {
            Color auxColor;
            int mediaColor;
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                        auxColor=new Color(biDestino.getRGB(i, j));
                        mediaColor=this.calcularMediaColor(auxColor);
                        biDestino.setRGB(i, j,this.colorRGBaSRGB(new Color(0,0,mediaColor,auxColor.getAlpha())));                                                               
                }
            }
         
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro6.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro4() throws RemoteException{
        byte[] bytesImg = new byte[1024*100];
        try {
            Color auxColor;
            int mediaColor;
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                        auxColor=new Color(biDestino.getRGB(i, j));
                        mediaColor=this.calcularMediaColor(auxColor);
                        biDestino.setRGB(i, j,this.colorRGBaSRGB(new Color(mediaColor,0,0,auxColor.getAlpha())));                                                                   
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro4.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
//        
        return bytesImg;
    }

    @Override
    public byte[] filtro5() throws RemoteException{
        byte[] bytesImg = new byte[1024*100];
        try {
            Color auxColor;
            int mediaColor;
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                        auxColor=new Color(biDestino.getRGB(i, j));
                        mediaColor=this.calcularMediaColor(auxColor);
                        biDestino.setRGB(i, j,this.colorRGBaSRGB(new Color(0,mediaColor,0,auxColor.getAlpha())));                                                                  
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro5.jpg");
            salida.write(bytesImg);
            salida.close();        
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro1() throws RemoteException{
        byte[] bytesImg = new byte[1024*100];
        try {
            int umbral=128;
            Color auxColor;
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                        auxColor=new Color(biDestino.getRGB(i, j));
                        biDestino.setRGB(i, j,this.colorRGBaSRGB(this.chequearUmbral(auxColor,umbral)));                                                                  
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro1.jpg");
            salida.write(bytesImg);
            salida.close();      
        } catch (IOException e) {
        }
        return bytesImg;
    }
    
    @Override
    public byte[] filtro3() throws RemoteException{
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    //se obtiene el color del pixel
                    Color color = new Color(biDestino.getRGB(i, j));
                    //se extraen los valores RGB
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                      //se coloca en la nueva imagen con los valores invertidos
                      biDestino.setRGB(i, j, new Color(255-r,255-g,255-b).getRGB());                                                                    
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro3.jpg");
            salida.write(bytesImg);
            salida.close();      
        } catch (IOException e) {
        }
        return bytesImg;
    }
    
    public ProcesamientoImagen() throws RemoteException{
      super(); //Llamada al constructor de la clase base (UnicastRemoteObject)
      // Código del constructor
    }
        
    public static void main(String[] args)
    {
        try
        { 
          b B = new b();
          Registry ma = LocateRegistry.createRegistry(1098);
          Manejo_Imagenes mir1 = new ProcesamientoImagen();
          System.out.println("Se ha iniciado el servidor");
          java.rmi.Naming.rebind("rmi://"+B.getIp()+":1098/PruebaRMI", mir1);
          System.out.println("Se ha iniciado el servidor1");
        }
        catch (MalformedURLException | RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public byte[] filtro7() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<(1*biDestino.getWidth())/2;i++){
                for(int j=0;j<biDestino.getWidth();j++){
                    Color color = new Color (biDestino.getRGB(i, j));
                    int r = color.getGreen();
                    int b =  color.getBlue();
                    biDestino.setRGB(i, j, new Color(0,0,b).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro7.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro8() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    biDestino.setRGB(i, j, new Color(r,0,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro8.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro9() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=(2*biDestino.getWidth())/3;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    int g = color.getAlpha();
                    biDestino.setRGB(i, j, new Color(r,0,0).getRGB());
                }
            }
            for(int i=0;i<(2*biDestino.getWidth())/2;i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color = new Color (biDestino.getRGB(i, j));
                    int r = color.getAlpha();
                    int b =  color.getBlue();
                    biDestino.setRGB(i, j, new Color(0,0,b).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro9.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro10() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    int g = color.getBlue();
                    biDestino.setRGB(i, j, new Color(r,0,g).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro10.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        
        return bytesImg;
    }

    @Override
    public byte[] filtro11() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getGreen();
                    int g = color.getBlue();
                    biDestino.setRGB(i, j, new Color(r,g,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro11.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        
        return bytesImg;
    }

    @Override
    public byte[] filtro12() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    int g = color.getBlue();
                    biDestino.setRGB(i, j, new Color(r,g,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro12.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        
        return bytesImg;
    }

    @Override
    public byte[] filtro13() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color = new Color (biDestino.getRGB(i, j));
                    int r = color.getGreen();
                    int b =  color.getBlue();
                    biDestino.setRGB(i, j, new Color(r,0,b).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro13.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        
        return bytesImg;
    }

    @Override
    public byte[] filtro14() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getHeight();i++){
                for(int j=0;j<biDestino.getWidth();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getAlpha();
                    biDestino.setRGB(i, j, new Color(r,0,g).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro14.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro15() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    int g = color.getGreen();
                    biDestino.setRGB(i, j, new Color(g,0,r).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro15.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro16() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    int g = color.getAlpha();
                    biDestino.setRGB(i, j, new Color(r,0,g).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro16.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro17() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    int g = color.getAlpha();
                    biDestino.setRGB(i, j, new Color(r,g,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro17.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro18() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getGreen();
                    int g = color.getAlpha();
                    biDestino.setRGB(i, j, new Color(r,g,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro18.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro19() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getGreen();
                    int g = color.getAlpha();
                    biDestino.setRGB(i, j, new Color(r,0,g).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro19.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro20() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getBlue();
                    int g = color.getAlpha();
                    biDestino.setRGB(i, j, new Color(r,0,g).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro20.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro21() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getBlue();
                    int g = color.getAlpha();
                    biDestino.setRGB(i, j, new Color(r,g,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro21.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro22() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getBlue();
                    biDestino.setRGB(i, j, new Color(r,0,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro22.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro23() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getRed();
                    biDestino.setRGB(i, j, new Color(r,0,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro23.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }

    @Override
    public byte[] filtro24() throws RemoteException {
        byte[] bytesImg = new byte[1024*100];
        try {
            InputStream in = new ByteArrayInputStream(fotos.get(contador-1));
            BufferedImage imagenOriginal = ImageIO.read(in);
            BufferedImage biDestino = (imagenOriginal.getSubimage(imagenOriginal.getMinX(), imagenOriginal.getMinY(), imagenOriginal.getWidth(), imagenOriginal.getHeight()));
            for(int i=0;i<biDestino.getWidth();i++){
                for(int j=0;j<biDestino.getHeight();j++){
                    Color color =  new Color(biDestino.getRGB(i, j));
                    int r = color.getGreen();
                    biDestino.setRGB(i, j, new Color(r,0,0).getRGB());
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( biDestino, "jpg", baos );
            baos.flush();
            bytesImg = baos.toByteArray();
            baos.close();
            FileOutputStream salida = new FileOutputStream("imagenfiltro24.jpg");
            salida.write(bytesImg);
            salida.close();
        } catch (IOException e) {
        }
        return bytesImg;
    }
}
