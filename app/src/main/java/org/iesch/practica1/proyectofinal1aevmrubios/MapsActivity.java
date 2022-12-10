package org.iesch.practica1.proyectofinal1aevmrubios;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.iesch.practica1.proyectofinal1aevmrubios.databinding.ActivityMapsBinding;
import org.iesch.practica1.proyectofinal1aevmrubios.modelo.PokeStop;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<PokeStop> paradas;
    private final String GYM = "GYM";
    private final String PARADA = "PARADA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        anadirPokeStop();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void anadirPokeStop() {
        paradas = new ArrayList<>();
        paradas.add(new PokeStop(getString(R.string.ayunta), GYM, 40.454232, -1.286719));
        paradas.add(new PokeStop(getString(R.string.correos), PARADA, 40.454196, -1.286918));
        paradas.add(new PokeStop(getString(R.string.ermitaAntonio), PARADA, 40.454955, -1.280357));
        paradas.add(new PokeStop(getString(R.string.skate), PARADA, 40.454957, -1.277186));
        paradas.add(new PokeStop(getString(R.string.casadecultura), PARADA, 40.452854, -1.286245));
        paradas.add(new PokeStop(getString(R.string.fronton), PARADA, 40.452001, -1.287335));
        paradas.add(new PokeStop(getString(R.string.pistaFutbol), PARADA, 40.451893, -1.287106));
        paradas.add(new PokeStop(getString(R.string.ermitaSebastian), PARADA, 40.456427, -1.292933));
        paradas.add(new PokeStop(getString(R.string.ermitaLorenzo), PARADA, 40.454500, -1.289306));
        paradas.add(new PokeStop(getString(R.string.parque), PARADA, 40.454606, -1.291563));
        paradas.add(new PokeStop(getString(R.string.pistaFutbol), PARADA, 40.455306, -1.291676));
        paradas.add(new PokeStop(getString(R.string.barras), PARADA, 40.454738, -1.292057));
        paradas.add(new PokeStop(getString(R.string.flores), PARADA, 40.452190, -1.287564));
        paradas.add(new PokeStop(getString(R.string.fuentePlaza), GYM, 40.454178, -1.286335));
        paradas.add(new PokeStop(getString(R.string.elCarro), GYM, 40.458092, -1.284542));
        paradas.add(new PokeStop(getString(R.string.piscina), GYM, 40.457789, -1.286995));
        paradas.add(new PokeStop(getString(R.string.fuente), GYM, 40.453661, -1.291450));
        paradas.add(new PokeStop(getString(R.string.plazadetoros), GYM, 40.455916, -1.293191));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng cella = new LatLng(40.454219, -1.286634);

        for (PokeStop i : paradas) {
            LatLng posicion = new LatLng(i.getLatitud(), i.getLongitud());
            String tipo = i.getTipo().toString();
            String nombre = i.getNombre().toString();
            int imgId = 0;
            switch (tipo) {
                case GYM:
                    imgId = R.drawable.gym;
                    break;
                case PARADA:
                    imgId = R.drawable.parada;
                    break;
            }
            mMap.addMarker(new MarkerOptions().position(posicion).icon(BitmapDescriptorFactory
                    .fromResource(imgId)).title(nombre));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cella, 17f));
    }
}