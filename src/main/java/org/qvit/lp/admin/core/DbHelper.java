package org.qvit.lp.admin.core;

import org.qvit.lp.admin.model.ClassInfo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by peng.liu11 on 2019/5/25.
 */
public interface DbHelper {
    char KEYWORD_APPEND_CHAR = '_';
    Set<String> PROJECT_FIELD_KEYWORD = new HashSet<>(Arrays.asList(new String[]{"length", "start", "searchType", "draw"}));
    Set<String> JAVA_KEYWORD = new HashSet<>(Arrays.asList(new String[]{"abstract",
            "assert",
            "boolean",
            "break",
            "byte",
            "case",
            "catch",
            "char",
            "class",
            "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "strictfp", "short", "static", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"}));

    List<ClassInfo> tableList(String url, String userName, String password) throws Exception;

    String dbName();
}
