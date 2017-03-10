package br.inpe.filesystem;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import br.inpe.repository.ImageRepository;
import nom.tam.fits.FitsException;

public class Controller {
	private final String pathDB;
	private final String pathPrincipal;
	
	@Autowired
	private ImageRepository imageRepository;
	
	public Controller(String pathPrincipal, String pathDB){
		this.pathPrincipal = pathPrincipal;
		this.pathDB = pathDB;
	
	}

	public ArrayList<String> getImages() throws IOException{
		return Find.getInstance().searchImage(this.pathPrincipal);	
	}
	
	public void sendToBD(ArrayList<String> pathImages) throws ParseException {
		
		for (String pathImage: pathImages){
			try{
				
				Image image = new Image(pathImage);
				
				String pathDestination = FileSystem.getInstance().createDir(pathImage, pathDB, pathPrincipal);
				FileSystem.getInstance().moveFile(pathImage, pathDestination);
				image.setKeyValue("FILESYSTEM", pathDestination);
				
				FileSystem.getInstance().deletePath(pathImage.substring(0, pathImage.lastIndexOf("/"))
						, this.pathPrincipal);
				
				ImagesCollection ima = new ImagesCollection();
				ima.setDocument(image.getDocument());
				
				this.imageRepository.insert(ima);
				
			}
			catch (Error e) {
				e.printStackTrace();

			} catch (FitsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//erro de puxar informacoes do fits
				e.printStackTrace();
			}
		}

	}

}
