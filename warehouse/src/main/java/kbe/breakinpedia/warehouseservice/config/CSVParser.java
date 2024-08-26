package kbe.breakinpedia.warehouseservice.config;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import kbe.breakinpedia.warehouseservice.model.Product;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVParser {

    /**
     * Methode to read cvs and create list of products
     *
     * @param reader to add csv file
     * @return list of product
     */
    public static List<Product> parse(Reader reader) {
        List<Product> products = new LinkedList<>();
        try {
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            products = csvReader.readAll().stream().map(data -> {
                Product product = new Product();
                product.setProductName(data[0]);
                product.setDescription(data[1]);
                product.setPrice(new BigDecimal(data[2]));
                product.setCounts(new BigDecimal(data[3]));
                product.setAuthor(data[4]);
                product.setImageUrl(data[5]);
                return product;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}