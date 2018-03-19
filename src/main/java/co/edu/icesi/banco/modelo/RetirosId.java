package co.edu.icesi.banco.modelo;
// Generated 15/08/2017 03:57:54 PM by Hibernate Tools 5.1.5.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RetirosId generated by hbm2java
 */
@Embeddable
public class RetirosId implements java.io.Serializable {

	private long retCodigo;
	private String cueNumero;

	public RetirosId() {
	}

	public RetirosId(long retCodigo, String cueNumero) {
		this.retCodigo = retCodigo;
		this.cueNumero = cueNumero;
	}

	@Column(name = "ret_codigo", nullable = false, precision = 10, scale = 0)
	public long getRetCodigo() {
		return this.retCodigo;
	}

	public void setRetCodigo(long retCodigo) {
		this.retCodigo = retCodigo;
	}

	@Column(name = "cue_numero", nullable = false, length = 30)
	public String getCueNumero() {
		return this.cueNumero;
	}

	public void setCueNumero(String cueNumero) {
		this.cueNumero = cueNumero;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RetirosId))
			return false;
		RetirosId castOther = (RetirosId) other;

		return (this.getRetCodigo() == castOther.getRetCodigo())
				&& ((this.getCueNumero() == castOther.getCueNumero()) || (this.getCueNumero() != null
						&& castOther.getCueNumero() != null && this.getCueNumero().equals(castOther.getCueNumero())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getRetCodigo();
		result = 37 * result + (getCueNumero() == null ? 0 : this.getCueNumero().hashCode());
		return result;
	}

}
