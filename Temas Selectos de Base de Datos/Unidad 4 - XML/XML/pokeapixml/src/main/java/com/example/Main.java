package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.util.HashMap;

import com.github.oscar0812.pokeapi.models.moves.Move;
import com.github.oscar0812.pokeapi.models.pokemon.Ability;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import com.github.oscar0812.pokeapi.models.pokemon.PokemonAbility;
import com.github.oscar0812.pokeapi.models.pokemon.PokemonMove;
import com.github.oscar0812.pokeapi.models.pokemon.Type;
import com.github.oscar0812.pokeapi.utils.Client;

public class Main {
    public static void main(String[] args) {
        HashMap<String, String> mapaTipos = new HashMap<String, String>();
        for (int i = 1; i <= 18; i++) {
            mapaTipos.put(Client.getTypeById(i).getName(), "T" + i);
        }

        HashMap<String, String> mapaMovimientos = new HashMap<String, String>();
        HashMap<String, String> mapaHabilidad = new HashMap<String, String>();

        File archivo = new File("pokedex.xml");
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo));
            escritor.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            escritor.write("<!DOCTYPE Pokedex SYSTEM \"pokedex.dtd\">\n");
            escritor.write("<Pokedex>\n");
            escritor.write("\t<Tipos>\n");
            for (int i = 1; i <= 18; i++) {
                Type tipo = Client.getTypeById(i);

                escritor.write("\t\t<Tipo id = \"" + mapaTipos.get(tipo.getName()) + "\">\n");
                escritor.write("\t\t\t<NombreTipo>" + tipo.getName() + "</NombreTipo>\n");
                escritor.write("\t\t\t<Inmunidad>\n");
                for (Type t : tipo.getDamageRelations().getNoDamageFrom()) {
                    escritor.write("\t\t\t\t<TipoInmune id = \"" + mapaTipos.get(t.getName()) + "\">" + t.getName()
                            + "</TipoInmune>\n");
                }
                escritor.write("\t\t\t</Inmunidad>\n");
                escritor.write("\t\t\t<Resistencia>\n");
                for (Type t : tipo.getDamageRelations().getHalfDamageFrom()) {
                    escritor.write("\t\t\t\t<TipoResistente id = \"" + mapaTipos.get(t.getName()) + "\">" + t.getName()
                            + "</TipoResistente>\n");
                }
                escritor.write("\t\t\t</Resistencia>\n");
                escritor.write("\t\t\t<Debilidad>\n");
                for (Type t : tipo.getDamageRelations().getDoubleDamageFrom()) {
                    escritor.write("\t\t\t\t<TipoDebil id = \"" + mapaTipos.get(t.getName()) + "\">" + t.getName()
                            + "</TipoDebil>\n");
                }
                escritor.write("\t\t\t</Debilidad>\n");
                escritor.write("\t\t</Tipo>\n");
            }

            escritor.write("\t</Tipos>\n");

            escritor.write("\t<Movimientos>\n");
            for (int i = 1; i <= 724; i++) {
                Move movimiento = Client.getMoveById(i);

                mapaMovimientos.put(movimiento.getName(), "M" + i);
                escritor.write(
                        "\t\t<Movimiento id = \"" + mapaMovimientos.get(movimiento.getName()) + "\" Categoria = \""
                                + movimiento.getDamageClass().getName() + "\" >\n");
                escritor.write("\t\t\t<NombreMovimiento>" + movimiento.getName() + "</NombreMovimiento>\n");
                escritor.write("\t\t\t<TipoMovimiento id = \"" + mapaTipos.get(movimiento.getType().getName()) + "\">"
                        + movimiento.getType().getName() + "</TipoMovimiento>\n");
                escritor.write("\t\t\t<PP>" + movimiento.getPp() + "</PP>\n");
                escritor.write("\t\t\t<Potencia>" + movimiento.getPower() + "</Potencia>\n");
                escritor.write("\t\t\t<Precision>" + movimiento.getAccuracy() + "</Precision>\n");

                escritor.write(
                        "\t\t\t<EfectoMovimiento>" + movimiento.getEffectEntries().get(0).getShortEffect()
                                + "</EfectoMovimiento>\n");

                escritor.write("\t\t</Movimiento>\n");
            }
            escritor.write("\t</Movimientos>\n");

            escritor.write("\t<Habilidades>\n");
            for (int i = 1; i <= 182; i++) {
                Ability habilidad = Client.getAbilityById(i);

                mapaHabilidad.put(habilidad.getName(), "H" + i);

                escritor.write("\t\t<Habilidad id = \"" + mapaHabilidad.get(habilidad.getName()) + "\">\n");
                escritor.write("\t\t\t<NombreHabilidad>" + habilidad.getName() + "</NombreHabilidad>\n");
                escritor.write("\t\t\t<EfectoHabilidad>" + habilidad.getEffectEntries().get(1).getShortEffect()
                        + "</EfectoHabilidad>\n");
                escritor.write("\t\t</Habilidad>\n");
            }
            escritor.write("\t</Habilidades>\n");

            escritor.write("\t<ListaPokemon>\n");
            for (int i = 1; i <= 807; i++) {
                Pokemon pokemon = Client.getPokemonById(i);

                escritor.write("\t\t<Pokemon id = \"P" + i + "\" altura = \"" + pokemon.getHeight() + "\" peso = \""
                        + pokemon.getWeight() + "\">\n");
                escritor.write("\t\t\t<NombrePokemon>" + pokemon.getName() + "</NombrePokemon>\n");
                escritor.write("\t\t\t<Tipo1 id = \"" + mapaTipos.get(pokemon.getTypes().get(0).getType().getName())
                        + "\">" + pokemon.getTypes().get(0).getType().getName() + "</Tipo1>\n");
                if (pokemon.getTypes().size() > 1) {
                    escritor.write("\t\t\t<Tipo2 id = \"" + mapaTipos.get(pokemon.getTypes().get(1).getType().getName())
                            + "\">" + pokemon.getTypes().get(1).getType().getName() + "</Tipo2>\n");
                }
                escritor.write("\t\t\t<HabilidadesPokemon>\n");
                for (PokemonAbility habilidad : pokemon.getAbilities()) {
                    if (mapaHabilidad.get(habilidad.getAbility().getName()) == null) {
                        continue;
                    }
                    escritor.write("\t\t\t\t<HabilidadPokemon id = \""
                            + mapaHabilidad.get(habilidad.getAbility().getName()) + "\" esOculta = \""
                            + habilidad.getIsHidden() + "\">"
                            + habilidad.getAbility().getName() + "</HabilidadPokemon>\n");
                }

                escritor.write("\t\t\t</HabilidadesPokemon>\n");

                escritor.write("\t\t\t<Estadisticas>\n");
                escritor.write("\t\t\t\t<Vida>" + pokemon.getStats().get(0).getBaseStat() + "</Vida>\n");
                escritor.write("\t\t\t\t<Ataque>" + pokemon.getStats().get(1).getBaseStat() + "</Ataque>\n");
                escritor.write("\t\t\t\t<Defensa>" + pokemon.getStats().get(2).getBaseStat() + "</Defensa>\n");
                escritor.write(
                        "\t\t\t\t<AtaqueEspecial>" + pokemon.getStats().get(3).getBaseStat() + "</AtaqueEspecial>\n");
                escritor.write(
                        "\t\t\t\t<DefensaEspecial>" + pokemon.getStats().get(4).getBaseStat() + "</DefensaEspecial>\n");
                escritor.write("\t\t\t\t<Velocidad>" + pokemon.getStats().get(5).getBaseStat() + "</Velocidad>\n");
                escritor.write("\t\t\t</Estadisticas>\n");

                escritor.write("\t\t\t<MovimientosPokemon>\n");
                for (PokemonMove movimiento : pokemon.getMoves()) {
                    if (mapaMovimientos.get(movimiento.getMove().getName()) == null) {
                        continue;
                    }
                    escritor.write("\t\t\t\t<MovimientoPokemon id = \""
                            + mapaMovimientos.get(movimiento.getMove().getName()) + "\">"
                            + movimiento.getMove().getName() + "</MovimientoPokemon>\n");
                }
                escritor.write("\t\t\t</MovimientosPokemon>\n");

                escritor.write("\t\t</Pokemon>\n");
            }
            escritor.write("\t</ListaPokemon>\n");
            escritor.write("</Pokedex>\n");

            escritor.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}