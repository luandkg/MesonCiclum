package com.luandkg.mesonciclum.MesonCiclum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// DESENVOLVEDOR : LUAN ALVES FREITAS
// DATA : 2019 12 28
// SISTEMA DE PONTUACAO POR RENDIMENTO 

public class MesonCiclum {


	public class Meson {

		private Date iData;
		private int iValor;

		public Meson(Date eData, int eValor) {
			iData = eData;
			iValor = eValor;
		}

		public Date Data() {
			return iData;
		}

		public int Valor() {
			return iValor;
		}

		public void setValor(int eValor) {
			iValor = eValor;
		}
	}

	private Date iDataInicial;
	private Date iDataFinal;

	private int iContador = 0;
	private Date iContadorData;

	private String iDetalhes = "";
	private String iCores = "";

	private ArrayList<Meson> Mesons = new ArrayList<Meson>();

	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public MesonCiclum() {
		iDataInicial = new Date(System.currentTimeMillis());

		Calendar c = Calendar.getInstance();
		c.setTime(iDataInicial);
		c.add(Calendar.DATE, +50);
		iDataFinal = c.getTime();

		for (int i = 0; i < 50; i++) {

			Calendar alterando = Calendar.getInstance();
			alterando.setTime(iDataInicial);
			alterando.add(Calendar.DATE, +i);
			Date alterada = alterando.getTime();

			Incluir(alterada, 0);
		}

		IniciarContador();

	}

	public void DefinirInicio(Date eDataInicial) {
		iDataInicial = eDataInicial;

		Mesons.clear();

		Calendar c = Calendar.getInstance();
		c.setTime(iDataInicial);
		c.add(Calendar.DATE, +50);
		iDataFinal = c.getTime();

		for (int i = 0; i < 50; i++) {

			Calendar alterando = Calendar.getInstance();
			alterando.setTime(iDataInicial);
			alterando.add(Calendar.DATE, +i);
			Date alterada = alterando.getTime();

			Incluir(alterada, 0);
		}

		IniciarContador();

	}

	public Date Inicio() {
		return iDataInicial;
	}

	public Date Final() {
		return iDataFinal;
	}

	public void Incluir(Date eData, int eValor) {

		boolean valida = false;
		if (eData.before(iDataInicial)) {
			valida = false;
		} else if (eData.after(iDataInicial)) {

			if (eData.before(iDataFinal)) {
				valida = true;
			} else if (eData.after(iDataFinal)) {
				valida = false;
			} else {
				valida = false;
			}

		} else {
			valida = true;
		}

		if (valida == true) {

			String DataCorrente = format.format(eData).toString();
			boolean existe = false;

			for (int i = 0; i < Mesons.size(); i++) {

				String DataIterada = format.format(Mesons.get(i).Data()).toString();

				if (DataCorrente.contentEquals(DataIterada)) {
					existe = true;
					Mesons.get(i).setValor(eValor);
					break;
				}

			}

			if (existe == false) {
				Meson MesonC = new Meson(eData, eValor);
				Mesons.add(MesonC);
			}

		}

	}

	public int Quantidade() {
		return Mesons.size();
	}

	public String Detalhes() {
		return iDetalhes;
	}

	public String Cores() {
		return iCores;
	}

	public float SomatorioCompleto() {
		return Somatorio(Final());
	}

	public float Somatorio(Date Ate) {
		float ret = 0;
		iDetalhes = "";
		iCores = "";

		int POS3 = 0;
		int POS5 = 0;
		int acabar = 0;

		float anterior = -99999;
		float maximo = -99999;

		for (int i = 0; i < Mesons.size(); i++) {
			int tipo = Mesons.get(i).Valor();

			if (tipo == 3) {
				ret += 10;
				acabar = 0;
				iDetalhes += "\n+ Conquista";
			} else if (tipo == 0) {
				ret += -20;
				iDetalhes += "\n- Multa";
				acabar += 1;
			}

			if (tipo == 3) {
				POS3 += 1;

				if (POS3 == 3) {
					ret += 15;
					POS3 = 0;
					iDetalhes += "\n+ Bonficação";
				}
			} else {
				POS3 = 0;
			}

			if (tipo == 3) {
				POS5 += 1;

				if (POS5 == 5) {
					ret += 30;
					POS5 = 0;
					iDetalhes += "\n+ Gratificação";
				}
			} else {
				POS5 = 0;
			}

			if (acabar >= 15) {
				iDetalhes += "\n- Destiu";
				ret = 0;
				break;
			}

			if (Mesons.get(i).Data().after(Ate)) {
				break;
			} else if (Mesons.get(i).Data().before(Ate)) {
			} else {
				break;
			}

			if (ret > anterior) {
				if (ret > maximo) {
					iCores += "V";
				} else {
					iCores += "A";
				}

			} else {
				iCores += "E";
			}
			anterior = ret;

			if (ret > maximo) {
				maximo = ret;
			}
		}

		return ret;
	}

	public void Limpar() {

		for (int i = 0; i < 50; i++) {

			Mesons.get(i).setValor(0);

		}

	}

	public float Listar() {
		float ret = 0;

		for (int i = 0; i < Mesons.size(); i++) {
			ret += Mesons.get(i).Valor();

			System.out.println(format.format(Mesons.get(i).Data()).toString() + " -> " + Mesons.get(i).Valor());
		}

		return ret;
	}

	public String Mapear() {

		String mapa = "";
		int c = 0;

		for (int i = 0; i < Mesons.size(); i++) {

			mapa += " " + Mesons.get(i).Valor();
			c += 1;

			if (c == 10) {
				c = 0;
				mapa += "\n";
			}
		}

		return mapa;
	}

	public void IniciarContador() {
		iContador = 0;
		iContadorData = Inicio();
	}

	public void Atribuir(int eValor) {
		Incluir(iContadorData, eValor);
	}

	public void Passar() {
		iContador += 1;

		Calendar alterando = Calendar.getInstance();
		alterando.setTime(iDataInicial);
		alterando.add(Calendar.DATE, +iContador);
		iContadorData = alterando.getTime();

	}

	public void AtribuirEPassar(int eValor) {
		Incluir(iContadorData, eValor);
		Passar();
	}

}
