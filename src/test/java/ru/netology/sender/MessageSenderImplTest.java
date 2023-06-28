package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MessageSenderTest {
    private GeoService geoService;
    private LocalizationService localizationService;
    private Map<String, String> headers;
    private MessageSender messageSender;

    @BeforeEach
    void SetUp() {
        geoService = Mockito.mock(GeoService.class);
        localizationService = Mockito.mock(LocalizationService.class);
        messageSender = new MessageSenderImpl(geoService, localizationService);
        headers = new HashMap<>();
    }

    @Test
    public void testSendmessageRussia() {
        Mockito.when(geoService.byIp("172."))
                .thenReturn(new Location(null, Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.");
        String actual = messageSender.send(headers);
        String expected = localizationService.locale(Country.RUSSIA);

        Assertions.assertEquals(expected, actual);
    }

}