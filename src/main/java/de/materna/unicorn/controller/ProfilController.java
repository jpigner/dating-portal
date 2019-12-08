package de.materna.unicorn.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

import de.materna.unicorn.model.Unicorn;
import de.materna.unicorn.service.UnicornService;
import de.materna.unicorn.utils.DatabaseHasher;
import lombok.Data;

/**
 * Represents the controller for the logging requests.
 */
@Data
@ApplicationScoped
@Named
public class ProfilController {
	private Optional<Unicorn> unicorn;
	private UploadedFile file;
	private String fileName;
	private String password;
	private String email;

	@Inject
	private FacesContext context;

	@Inject
	private Logger log;

	@Inject
	private UnicornService unicornService;

	@Inject
	private DatabaseHasher hasher;

	/**
	 * Initialized a unicorn.
	 */
	@PostConstruct
	public void init() {
		unicorn = Optional.of(new Unicorn());
	}

	/**
	 * Checks if login is valid and returns own profile.
	 * 
	 * @return restpoint to the own profile if the login is valid or redirect to the
	 *         login.
	 */
	public String login() {
		unicorn = unicornService.findByEmail(email);
		password = hasher.getHashedPassword(password);

		if (password != null || email != null) {
			if (password.equals(unicorn.get().getPassword()) && email.equals(unicorn.get().getEmail())) {
				return "/profil.xhtml?faces-redirect=true&id=" + unicorn.get().getId();
			}
			return "register";
		}
		return "register";
	}

	/**
	 * Logs out the logged unicorn.
	 * 
	 * @return restpoint to the login page.
	 */
	public String logout() {
		unicorn = Optional.of(new Unicorn());
		String errorMessage = "Du bist ausgeloggt. Bitte logge dich ein!";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
		context.addMessage(null, message);
		return "login";
	}

	/**
	 * Redirects to the requested profile.
	 * 
	 * @return restpoint to the requested profile.
	 */
	public String profil() {
		return "/profil.xhtml?faces-redirect=true&id=" + unicorn.get().getId();
	}

	/**
	 * @throws IOException if error occurs while writing the file.
	 */
	public void upload() throws IOException {
		if (file != null) {
			FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	/**
	 * Uploads the image.
	 * 
	 * @param event file event from the profil controller.
	 * @throws Exception if error occurs while writing the file.
	 */
	public void handleFileUpload(FileUploadEvent event) throws Exception {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		String id = unicorn.get().getFotoid().replaceAll("[^0-9]", "");

		String fileType = FilenameUtils.getExtension(event.getFile().getFileName());
		fileType = fileType.equals("jpeg") ? "jpg" : fileType;

		String path = "../../../../Desktop/WS_EE/Date4You/src/main/webapp/resources/img/user/avatar/unicorn"
				+ String.valueOf(id) + "." + fileType;

		fileName = "unicorn" + String.valueOf(id) + "." + fileType;
		log.info(fileName);
		log.info(Paths.get(path) + "");
		Files.write(Paths.get(path), event.getFile().getContents());
	}

}
