package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


public class DataUtility {

    private static SimpleDateFormat formatoPadrao = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Método para verificar se a data informada é maior que a data de hoje.
     *
     * @param dataInformada
     * @return boolean
     */
    public static boolean iSdataMaiorQueHoje(Date dataInformada) {
        return dataInformada.after(new Date());
    }

    /**
     * Método para verificar se a data informada é menor que a data de hoje.
     *
     * @param dataInformada
     * @return boolean
     */
    public boolean iSdataMenorQueHoje(Date dataInformada) {
        return dataInformada.before(new Date());
    }

    /**
     * Método para verificar se a data informada é hoje.
     *
     * @param dataInformada
     * @return boolean
     */
    public boolean iSdataHoje(Date dataInformada) {
        return dataInformada.equals(new Date());
    }

    /**
     * Método para formatar uma data no formato padrao dd/MM/yyyy
     *
     * @param data
     * @return String
     */
    public static String formatarDataComPadrao(Date data) {
        if (data == null) {
            return null;
        }
        return formatoPadrao.format(data);
    }

    /**
     * Método para formatar uma data no formato Padrao informado
     *
     * @param data
     * @param formato
     * @return String
     */
    public static String formatarDataComPadrao(Date data, String formato) {
        if (data == null || formato == null || formato.equals("")) {
            return null;
        }
        formatoPadrao.applyPattern(formato);
        String dataFormatada = formatoPadrao.format(data);
        formatoPadrao.applyPattern("dd/MM/yyyy");
        return dataFormatada;
    }

    /**
     * Método que transforma um objeto do tipo Date em um objeto LocalDate
     *
     * @param Date
     * @return LocalDate
     *
     */
    public static LocalDate dateParaLocalDate(Date data) {
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static java.sql.Date dateParaDateSql(Date data) {
        return new java.sql.Date(data.getTime());
    }

    public static int calcularIdade(Date dataNascimento) {
        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(dateParaLocalDate(dataNascimento), hoje);
        return periodo.getYears();
    }

    /**
     * Método que transforma uma String em um objeto Date
     *
     * @param String
     * @return Date
     *
     */
    public static Date transformarStringEmDate(String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        Date dataParse = null;
        try {
            dataParse = formatoPadrao.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        return dataParse;
    }

    /**
     * Retorna o dia da semana que vai cair a data
     *
     * @param data Date
     * @return Integer Calendar.DAY_OF_WEEK
     */
    public static Integer retornarDiaDaSemana(Date data) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        return calendario.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Verifica se a data é Sexta, Sábado ou Domingo.
     *
     * @param data A data.
     * @return Verdadeiro se for Sexta, Sábado ou Domingo.
     */
    public static boolean isSextaSabadoDomingo(Date data) {
        Integer diaSemana = retornarDiaDaSemana(data);
        if (diaSemana == Calendar.FRIDAY || diaSemana == Calendar.SATURDAY
                || diaSemana == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

}
