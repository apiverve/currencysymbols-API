// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     CurrencySymbolsData data = Converter.fromJsonString(jsonString);

package com.apiverve.currencysymbols.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static CurrencySymbolsData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(CurrencySymbolsData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(CurrencySymbolsData.class);
        writer = mapper.writerFor(CurrencySymbolsData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// CurrencySymbolsData.java

package com.apiverve.currencysymbols.data;

import com.fasterxml.jackson.annotation.*;

public class CurrencySymbolsData {
    private CountriesFound[] countriesFound;
    private Currency currency;

    @JsonProperty("countriesFound")
    public CountriesFound[] getCountriesFound() { return countriesFound; }
    @JsonProperty("countriesFound")
    public void setCountriesFound(CountriesFound[] value) { this.countriesFound = value; }

    @JsonProperty("currency")
    public Currency getCurrency() { return currency; }
    @JsonProperty("currency")
    public void setCurrency(Currency value) { this.currency = value; }
}

// CountriesFound.java

package com.apiverve.currencysymbols.data;

import com.fasterxml.jackson.annotation.*;

public class CountriesFound {
    private String countryName;
    private CurrencyName currencyName;
    private Currency currencyISOCode;
    private String currencyNumber;
    private String currencyMnrUnts;
    private CurrencySymbol currencySymbol;

    @JsonProperty("country_name")
    public String getCountryName() { return countryName; }
    @JsonProperty("country_name")
    public void setCountryName(String value) { this.countryName = value; }

    @JsonProperty("currency_name")
    public CurrencyName getCurrencyName() { return currencyName; }
    @JsonProperty("currency_name")
    public void setCurrencyName(CurrencyName value) { this.currencyName = value; }

    @JsonProperty("currency_iso_code")
    public Currency getCurrencyISOCode() { return currencyISOCode; }
    @JsonProperty("currency_iso_code")
    public void setCurrencyISOCode(Currency value) { this.currencyISOCode = value; }

    @JsonProperty("currency_number")
    public String getCurrencyNumber() { return currencyNumber; }
    @JsonProperty("currency_number")
    public void setCurrencyNumber(String value) { this.currencyNumber = value; }

    @JsonProperty("currency_mnr_unts")
    public String getCurrencyMnrUnts() { return currencyMnrUnts; }
    @JsonProperty("currency_mnr_unts")
    public void setCurrencyMnrUnts(String value) { this.currencyMnrUnts = value; }

    @JsonProperty("currency_symbol")
    public CurrencySymbol getCurrencySymbol() { return currencySymbol; }
    @JsonProperty("currency_symbol")
    public void setCurrencySymbol(CurrencySymbol value) { this.currencySymbol = value; }
}

// Currency.java

package com.apiverve.currencysymbols.data;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Currency {
    USD;

    @JsonValue
    public String toValue() {
        switch (this) {
            case USD: return "USD";
        }
        return null;
    }

    @JsonCreator
    public static Currency forValue(String value) throws IOException {
        if (value.equals("USD")) return USD;
        throw new IOException("Cannot deserialize Currency");
    }
}

// CurrencyName.java

package com.apiverve.currencysymbols.data;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum CurrencyName {
    US_DOLLAR;

    @JsonValue
    public String toValue() {
        switch (this) {
            case US_DOLLAR: return "US Dollar";
        }
        return null;
    }

    @JsonCreator
    public static CurrencyName forValue(String value) throws IOException {
        if (value.equals("US Dollar")) return US_DOLLAR;
        throw new IOException("Cannot deserialize CurrencyName");
    }
}

// CurrencySymbol.java

package com.apiverve.currencysymbols.data;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum CurrencySymbol {
    EMPTY;

    @JsonValue
    public String toValue() {
        switch (this) {
            case EMPTY: return "$";
        }
        return null;
    }

    @JsonCreator
    public static CurrencySymbol forValue(String value) throws IOException {
        if (value.equals("$")) return EMPTY;
        throw new IOException("Cannot deserialize CurrencySymbol");
    }
}