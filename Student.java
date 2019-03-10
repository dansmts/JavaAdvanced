package be.studenten;

public class Student {
    private String naam;
    private String voornaam;
    private int leeftijd;
    private String adres;
    private String huisnr;
    private String postcode;
    private String stad;
    private String land;

    public Student(String naam, String voornaam, int leeftijd, String adres, String huisnr, String postcode, String stad, String land) {
        this.naam = naam;
        this.voornaam = voornaam;
        this.leeftijd = leeftijd;
        this.adres = adres;
        this.huisnr = huisnr;
        this.postcode = postcode;
        this.stad = stad;
        this.land = land;
    }

    public Student(String[] studentString){
        this.naam = studentString[0];
        this.voornaam = studentString[1];
        this.leeftijd = Integer.parseInt(studentString[2]);
        this.adres = studentString[3];
        this.huisnr = studentString[4];
        this.postcode = studentString[5];
        this.stad = studentString[6];
        this.land = studentString[7];
    }

    public String getNaam() {
        return naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public String getAdres() {
        return adres;
    }

    public String getHuisnr() {
        return huisnr;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStad() {
        return stad;
    }

    public String getLand() {
        return land;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%d;%s;%s;%s;%s;%s", naam, voornaam, leeftijd, adres, huisnr, postcode, stad, land);
    }
}
