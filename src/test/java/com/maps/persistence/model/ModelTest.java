package com.maps.persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest// @ActiveProfiles("homolog")
class ModelTest {
    //UNIT TESTS(& COMPONENTS): VALIDATION, EXCEPTION
    //INTEGRATION TESTS: API INTEGRATION, REPOSITORY LAYER, API VALIDATION, DB INTEGRATION.

    Country country;
    State state;
    ChartArea chartArea = new ChartArea();

    public GeometryFactory geometryFactory;
    public Coordinate[] coordinates;
    public CoordinateSequence coordinateSequence;
    public Point point;
    public LinearRing linearRing;

    @BeforeEach
    public void setUp() {
        geometryFactory = new GeometryFactory();
        coordinates = new Coordinate[]{new Coordinate(0, 0)};
        coordinateSequence = new CoordinateArraySequence(coordinates);
        point = new Point(coordinateSequence, geometryFactory);
        linearRing = geometryFactory.createLinearRing();

        country = new Country((int)(Math.random() * Math.random()), "Gadelha");
        state = new State(0, "Gadelha", country);
        chartArea = new ChartArea("Gadelha");
    }
    @Test
    public void BlindSignalTest() {
        BlindSignal blindSignal = new BlindSignal(0, "name", "chart", point, "description", "observation", chartArea, state);
        assertEquals("name", blindSignal.getName());
        assertTrue(point.contains(point));
    }
    @Test
    public void ChartTest() {
        Collection<LocalDateTime> localDateTimes = new ArrayList<>();
        Chart chart = new Chart("number", "title", 0, localDateTimes, point, point, chartArea);
        assertEquals("number", chart.getNumber());
    }
    @Test
    public void ChartAreaTest() {
        assertEquals("Gadelha", chartArea.getName());
    }
    @Test
    public void CityTest() {
        City city = new City(0, "Gadelha", state);
        assertEquals("Gadelha", city.getName());
    }
    @Test
    public void CountryTest() {
        assertEquals("Gadelha", country.getName());
    }
    @Test
    public void GaugeStationTest() {
        GaugeStation gaugeStation = new GaugeStation("number", "title", point, state, chartArea);
        assertEquals("number", gaugeStation.getNumber());
    }
    @Test
    public void InternationalChartTest() {
        InternationalChart internationalChart = new InternationalChart("number");
        assertEquals("number", internationalChart.getInternationalNumber());
    }
    @Test
    public void LocalChartTest() {
        LocalChart localChart = new LocalChart("name", "areaType", point);
        assertEquals("name", localChart.getName());
    }
    @Test
    public void MaritimeAreaTest() {
        Polygon polygon = new Polygon(linearRing, null, geometryFactory);
        Polygon[] polygons = new Polygon[1];
        polygons[0] = polygon;
        MultiPolygon multiPolygon = new MultiPolygon(polygons, geometryFactory);
        MaritimeArea maritimeArea = new MaritimeArea("code", "name", "start", "finish", polygon, multiPolygon);
        assertEquals("name", maritimeArea.getName());
    }
    @Test
    public void RoleTest() {
        Set<Privilege> privilege = new HashSet<>();
        Role role = new Role("Gadelha", privilege);
        assertEquals("Gadelha", role.getName());
    }
    @Test
    public void StateTest() {
//        assertThrows();
//        assertNotNull(state);
//        assertNotEquals(state, state);
        assertEquals("Gadelha", state.getName());
    }
    @Test
    public void UserTest() {
        Set<Role> role = new HashSet<>();
        User user = new User();
        assertEquals("Gadelha", user.getUsername());
    }
    @Test
    public void UserDTOTest() {
        Set<Role> role = new HashSet<>();
        User user = new User();
        assertEquals("Gadelha", user.getUsername());
    }
}