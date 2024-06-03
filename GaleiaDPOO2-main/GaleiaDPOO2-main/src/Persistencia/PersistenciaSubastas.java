package Persistencia;

import Model.GaleriaDeArte;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaSubastas {

    public JSONArray salvarSubastas(GaleriaDeArte galeria) {
        JSONArray jSubastas = new JSONArray();
        List<List<String>> subastasGaleria = galeria.getRegistrosPorSubasta();

        for (List<String> subasta : subastasGaleria) {
            JSONArray jSubasta = new JSONArray();
            for (String puja : subasta) {
                jSubasta.put(puja);
            }
            jSubastas.put(jSubasta);
        }

        return jSubastas;
    }

    public List<List<String>> cargarSubastas(JSONArray jSubastas) {
        List<List<String>> subastas = new ArrayList<>();

        for (int i = 0; i < jSubastas.length(); i++) {
            JSONArray jSubasta = jSubastas.getJSONArray(i);
            List<String> subasta = new ArrayList<>();
            for (int j = 0; j < jSubasta.length(); j++) {
                subasta.add(jSubasta.getString(j));
            }
            subastas.add(subasta);
        }

        return subastas;
    }
}

