package Usuario;

public abstract class Usuario {
	
	private String password;
	
	private String login;
	
	private String nombre;
	
	public Usuario(String password, String login, String nombre) {
		this.password = password;
		this.login = login;
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public String getLogin() {
		return login;
	}

	public String getNombre() {
		return nombre;
	}
	
	public abstract String getTipoUsuario();
}