package com.github.hideto0710;
import com.google.zetasql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    protected static AnalyzerOptions enableAllFeatures() {
        AnalyzerOptions analyzerOptions = new AnalyzerOptions();
        analyzerOptions.setLanguageOptions(new LanguageOptions().enableMaximumLanguageFeatures());
        analyzerOptions.setPruneUnusedColumns(true);

        return analyzerOptions;
    }

    protected static SimpleCatalog buildCatalog() {
        SimpleCatalog catalog = new SimpleCatalog("queryCatalog");
        catalog.addZetaSQLFunctions(new ZetaSQLBuiltinFunctionOptions());
        List<SimpleColumn> sampleColumns = new ArrayList<>(
                Arrays.asList(
                        new SimpleColumn(
                                "sample",
                                "a",
                                TypeFactory.createSimpleType(ZetaSQLType.TypeKind.TYPE_STRING)),
                        new SimpleColumn(
                                "sample",
                                "b",
                                TypeFactory.createSimpleType(ZetaSQLType.TypeKind.TYPE_FLOAT))
                )
        );
        List<SimpleColumn> masterColumns = new ArrayList<>(
                Arrays.asList(
                        new SimpleColumn(
                                "master",
                                "a",
                                TypeFactory.createSimpleType(ZetaSQLType.TypeKind.TYPE_STRING)),
                        new SimpleColumn(
                                "master",
                                "c",
                                TypeFactory.createSimpleType(ZetaSQLType.TypeKind.TYPE_FLOAT))
                )
        );
        catalog.addSimpleTable(new SimpleTable("sample", sampleColumns));
        catalog.addSimpleTable(new SimpleTable("master", masterColumns));
        return catalog;
    }
}
