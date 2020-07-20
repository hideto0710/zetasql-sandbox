package com.github.hideto0710;

import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import com.google.zetasql.Analyzer;
import com.google.zetasql.resolvedast.ResolvedColumn;
import com.google.zetasql.resolvedast.ResolvedNodes;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainTest {
    private String readSQL(String path) {
        InputStream is = Main.class.getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.lines().collect(Collectors.joining(" "));
    }

    @Test
    public void simpleOutputList() {
        String query = readSQL("/simple.sql");
        ResolvedNodes.ResolvedStatement statement =
                Analyzer.analyzeStatement(query, Main.enableAllFeatures(), Main.buildCatalog());

        List<String> actual = new java.util.ArrayList<>(Collections.emptyList());
        statement.accept(
                new ResolvedNodes.Visitor() {
                    @Override
                    public void visit(ResolvedNodes.ResolvedOutputColumn outputColumn) {
                        ResolvedColumn resolvedColumn = outputColumn.getColumn();
                        actual.add(resolvedColumn.getTableName() + "." + outputColumn.getName());
                        super.visit(outputColumn);
                    }
                }
        );
        assertThat(actual, containsInAnyOrder("sample.a", "sample.b", "master.c"));
    }
    @Test
    public void aggregateOutputList() {
        String query = readSQL("/aggregate.sql");
        ResolvedNodes.ResolvedStatement statement =
                Analyzer.analyzeStatement(query, Main.enableAllFeatures(), Main.buildCatalog());

        List<String> actual = new java.util.ArrayList<>(Collections.emptyList());
        statement.accept(
                new ResolvedNodes.Visitor() {
                    @Override
                    public void visit(ResolvedNodes.ResolvedOutputColumn outputColumn) {
                        ResolvedColumn resolvedColumn = outputColumn.getColumn();
                        actual.add(resolvedColumn.getTableName() + "." + outputColumn.getName());
                        super.visit(outputColumn);
                    }
                }
        );
        assertThat(actual, containsInAnyOrder("$groupby.a", "$aggregate.$col2"));
    }
    @Test
    public void withOutputList() {
        String query = readSQL("/with.sql");
        ResolvedNodes.ResolvedStatement statement =
                Analyzer.analyzeStatement(query, Main.enableAllFeatures(), Main.buildCatalog());

        List<String> actual = new java.util.ArrayList<>(Collections.emptyList());
        statement.accept(
                new ResolvedNodes.Visitor() {
                    @Override
                    public void visit(ResolvedNodes.ResolvedOutputColumn outputColumn) {
                        ResolvedColumn resolvedColumn = outputColumn.getColumn();
                        actual.add(resolvedColumn.getTableName() + "." + outputColumn.getName());
                        super.visit(outputColumn);
                    }
                }
        );
        assertThat(actual, containsInAnyOrder("sample.a", "sample.b", "with_m.c"));
    }
}
