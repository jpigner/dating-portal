package de.materna.unicorn.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

import de.materna.unicorn.repository.UnicornListProducer;
import lombok.Data;

@Data
@Named
@ApplicationScoped
public class FileUpload {
	private UploadedFile file;
	private String fileName;
	
	@Inject
	private Logger log;
	
	@Inject
	private UnicornListProducer list;

	public void upload( ) throws IOException {
		if ( file != null ) {
			FacesMessage message = new FacesMessage( "Succesful", file.getFileName( ) + " is uploaded." );
			FacesContext.getCurrentInstance( ).addMessage( null , message );
		}
	}

	public void handleFileUpload( FileUploadEvent event ) throws Exception {
		FacesMessage msg = new FacesMessage( "Succesful", event.getFile( ).getFileName( ) + " is uploaded." );
		FacesContext.getCurrentInstance( ).addMessage( null , msg );

		int id = ( int ) list.getUnicorns( ).stream( ).mapToDouble( unicorn -> unicorn.getId( ) ).max( ).getAsDouble( )
				+ 1;
		String fileType = FilenameUtils.getExtension( event.getFile( ).getFileName( ) );
		fileType = fileType.equals( "jpeg" ) ? "jpg" : fileType;
		
		String path = "../../../../Desktop/WS_Trainee/Date4You/src/main/webapp/resources/img/user/avatar/unicorn"
				+ String.valueOf( id ) + "." + fileType;
		
		fileName = "unicorn" + String.valueOf( id ) + "." +fileType;
		Files.write( Paths.get( path ) , event.getFile( ).getContents( ) );
	}
}