package br.inpe.filesystem;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.model.FileSystem;
import br.inpe.model.Find;
import br.inpe.model.Image;
import br.inpe.model.ImagesCollection;
import br.inpe.repository.ImageRepository;
import br.inpe.service.ImageServiceImpl;
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
	
	public void sendToBD(ArrayList<String> pathImages) {
		
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
			catch (FitsException e) {
				System.out.println("É imagem fits?");
				e.printStackTrace();
			} catch (IOException e) {
				//log
				System.out.println("Erro");
				e.printStackTrace();
			} catch (ParseException e) {
				System.out.println("Erro Parse");
				e.printStackTrace();
			}
		}

	}

}
