package e1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


class AldeaTest {

    static Aldea gala;
    static Aldea teutones;
    static Aldea romana;

    @BeforeAll
    public static void crearAldeas(){
        gala = new AldeaGala("Galia", 500, 10);
        gala.agregarTropa(new Druida());
        gala.agregarTropa(new RayoTeutates());
        RayoTeutates rt1 = new RayoTeutates();
        rt1.equiparArmaduraPesada();
        gala.agregarTropa(rt1);
        gala.agregarTropa(new Falange());

        // Crear aldea teutona
        teutones = new AldeaTeutona("Teutonia", 300, 5);
        teutones.agregarTropa(new GuerreroHacha());
        teutones.agregarTropa(new GuerreroPorra());
        GuerreroPorra gp1 = new GuerreroPorra();
        gp1.mejorarArma();
        teutones.agregarTropa(gp1);
        teutones.agregarTropa(new Paladin());

        // Crear aldea romana
        romana = new AldeaRomana("Roma", 200, 10);
        romana.agregarTropa(new Legionario());
        romana.agregarTropa(new Pretoriano());
        Pretoriano p1 = new Pretoriano();
        p1.equiparArmadura();
        romana.agregarTropa(p1);
        romana.agregarTropa(new EquitesImperatoris());
    }

    @Test
    public void testBatallaGaulsVsTeutons() {
        // Crear aldea gala


        // Crear simulador
        Simulador simulador = new Simulador();
        List<String> resultado = simulador.batalla(gala, teutones);

        // Cálculo del poder de ataque y defensa
        int poderAtaqueGalia = (int)((45+100+75+15)*1.2); // Total de ataque de la aldea gala
        int poderDefensaTeutones = (30+100+20+20- teutones.getNivelMuralla()); // Total de defensa de la aldea teutona

        // Comprobar el resultado
        assertEquals("### Starts the battle ! --> Galia Attacks Teutonia ! ###", resultado.get(0));
        assertEquals("Galia have the following soldiery :", resultado.get(1));
        assertEquals("Galia Soldiery - Druidrider", resultado.get(2));
        assertEquals("Galia Soldiery - Theutates Thunder", resultado.get(3));
        assertEquals("Galia Soldiery - Theutates Thunder with heavy armor", resultado.get(4));
        assertEquals("Galia Soldiery - Phalanx", resultado.get(5));
        assertEquals("Total Galia attack power : " + poderAtaqueGalia, resultado.get(6));

        // Comprobar los soldados de la aldea teutona
        assertEquals("Teutonia have the following soldiery :", resultado.get(7));
        assertEquals("Teutonia Soldiery - Axeman", resultado.get(8));
        assertEquals("Teutonia Soldiery - Maceman", resultado.get(9));
        assertEquals("Teutonia Soldiery - Maceman with iron mace", resultado.get(10));
        assertEquals("Teutonia Soldiery - Paladin", resultado.get(11));
        assertEquals("Total Teutonia defense power : " + poderDefensaTeutones, resultado.get(12));

        // Comprobar el ganador
        assertEquals("Galia with an age of 500 years WINS !", resultado.getLast());
    }



    @Test
    public void testBatallaRomanosVsGalos() {


        // Crear simulador
        Simulador simulador = new Simulador();
        List<String> resultado = simulador.batalla(romana, gala);

        // Cálculo del poder de ataque y defensa
        int poderAtaqueRoma = ((int) ((40+30+120+30)*1.1)); // 10% aumento
        int poderDefensaGalia = (int) ((115+25+25*1.25+40)+1.5* gala.nivelMuralla); // 1.5 puntos por cada nivel de resistencia


        assertEquals("### Starts the battle ! --> Roma Attacks Galia ! ###", resultado.get(0));
        assertEquals("Roma have the following soldiery :", resultado.get(1));
        assertEquals("Roma Soldiery - Legionnaire", resultado.get(2));
        assertEquals("Roma Soldiery - Praetorian", resultado.get(3));
        assertEquals("Roma Soldiery - Praetorian with armor", resultado.get(4));
        assertEquals("Roma Soldiery - Equites Imperatoris", resultado.get(5));
        assertEquals("Total Roma attack power : " + poderAtaqueRoma, resultado.get(6));

        assertEquals("Galia have the following soldiery :", resultado.get(7));
        assertEquals("Galia Soldiery - Druidrider", resultado.get(8));
        assertEquals("Galia Soldiery - Theutates Thunder", resultado.get(9));
        assertEquals("Galia Soldiery - Theutates Thunder with heavy armor", resultado.get(10));
        assertEquals("Galia Soldiery - Phalanx", resultado.get(11));
        assertEquals("Total Galia defense power : " + poderDefensaGalia, resultado.get(12));


        // Comprobar el ganador
        assertEquals("Roma with an age of 200 years WINS !", resultado.getLast());
    }

    @Test
    public void testBatallaTeutonesVsRomanos() {


        // Crear simulador
        Simulador simulador = new Simulador();
        List<String> resultado = simulador.batalla(teutones, romana);

        // Cálculo del poder de ataque y defensa
        int poderAtaqueTeutonia = (int) ((60+40+40*1.25+55)*0.95); // 5% disminución
        int poderDefensaRoma = (int) (35+65+65*1.1+65+2*romana.nivelMuralla); // 2 puntos por cada nivel de resistencia

        // Comprobar el resultado

        assertEquals("### Starts the battle ! --> Teutonia Attacks Roma ! ###", resultado.get(0));
        assertEquals("Teutonia have the following soldiery :", resultado.get(1));
        assertEquals("Teutonia Soldiery - Axeman", resultado.get(2));
        assertEquals("Teutonia Soldiery - Maceman", resultado.get(3));
        assertEquals("Teutonia Soldiery - Maceman with iron mace", resultado.get(4));
        assertEquals("Teutonia Soldiery - Paladin", resultado.get(5));
        assertEquals("Total Teutonia attack power : " + poderAtaqueTeutonia, resultado.get(6));

        assertEquals("Roma have the following soldiery :", resultado.get(7));
        assertEquals("Roma Soldiery - Legionnaire", resultado.get(8));
        assertEquals("Roma Soldiery - Praetorian", resultado.get(9));
        assertEquals("Roma Soldiery - Praetorian with armor", resultado.get(10));
        assertEquals("Roma Soldiery - Equites Imperatoris", resultado.get(11));
        assertEquals("Total Roma defense power : " + poderDefensaRoma, resultado.get(12));
    }

    //Comprobamos que no se puedan añadir guerreros de otras aldeas

    @Test
    public void testGuerrerosOtrasAldeaGala() {
        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new Pretoriano()));

        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new Legionario()));

        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new EquitesImperatoris()));

        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new GuerreroPorra()));

        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new GuerreroHacha()));

        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new Paladin()));

        GuerreroPorra gp2 = new GuerreroPorra();
        gp2.mejorarArma();
        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(gp2));

        Pretoriano p1 = new Pretoriano();
        p1.equiparArmadura();
        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(p1));
    }

    @Test
    public void testGuerrerosOtrasAldeaRomana() {
        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(new Druida()));

        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(new RayoTeutates()));

        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(new Falange()));

        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(new GuerreroPorra()));

        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(new GuerreroHacha()));

        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(new Paladin()));

        GuerreroPorra gp2 = new GuerreroPorra();
        gp2.mejorarArma();
        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(gp2));

        RayoTeutates rt2 = new RayoTeutates();
        rt2.equiparArmaduraPesada();
        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(rt2));
    }

    @Test
    public void testGuerrerosOtrasAldeaTeutona() {
        assertThrows(IllegalArgumentException.class, () -> teutones.agregarTropa(new Druida()));

        assertThrows(IllegalArgumentException.class, () -> teutones.agregarTropa(new RayoTeutates()));

        assertThrows(IllegalArgumentException.class, () -> teutones.agregarTropa(new Falange()));

        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new Pretoriano()));

        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new Legionario()));

        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(new EquitesImperatoris()));

        RayoTeutates rt2 = new RayoTeutates();
        rt2.equiparArmaduraPesada();
        assertThrows(IllegalArgumentException.class, () -> romana.agregarTropa(rt2));

        Pretoriano p1 = new Pretoriano();
        p1.equiparArmadura();
        assertThrows(IllegalArgumentException.class, () -> gala.agregarTropa(p1));
    }


}


