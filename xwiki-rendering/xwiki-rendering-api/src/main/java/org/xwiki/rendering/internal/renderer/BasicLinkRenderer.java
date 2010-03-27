/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.internal.renderer;

import org.apache.commons.lang.StringUtils;
import org.xwiki.rendering.listener.Link;
import org.xwiki.rendering.listener.LinkType;
import org.xwiki.rendering.parser.LinkParser;

/**
 * Generate a string representation of a {@link}'s reference using the format:
 * {@code (reference)[#anchor][?queryString][@interwikialias]}.

 * @version $Id$
 * @since 2.1M1
 */
public class BasicLinkRenderer
{
    /**
     * Escapes to add when rendering a link reference part.
     */
    private static final String[] ESCAPE_REPLACEMENTS_REFERENCE = new String[] {
        LinkParser.ESCAPE_CHAR + LinkParser.SEPARATOR_QUERYSTRING,
        LinkParser.ESCAPE_CHAR + LinkParser.SEPARATOR_INTERWIKI,
        LinkParser.ESCAPE_CHAR + LinkParser.SEPARATOR_ANCHOR};

    /**
     * Replacement chars for the escapes to add to the reference part.
     */
    private static final String[] ESCAPES_REFERENCE = new String[] {
        LinkParser.SEPARATOR_QUERYSTRING,
        LinkParser.SEPARATOR_INTERWIKI,
        LinkParser.SEPARATOR_ANCHOR};

    /**
     * Escapes to add when rendering a link query string, anchor or interwiki part.
     */
    private static final String[] ESCAPE_REPLACEMENTS_EXTRA = new String[] {
        LinkParser.ESCAPE_CHAR + LinkParser.SEPARATOR_QUERYSTRING,
        LinkParser.ESCAPE_CHAR + LinkParser.SEPARATOR_INTERWIKI,
        LinkParser.ESCAPE_CHAR + LinkParser.SEPARATOR_ANCHOR,
        "" + LinkParser.ESCAPE_CHAR + LinkParser.ESCAPE_CHAR};

    /**
     * Replacement chars for the escapes to add to the query string, anchor or interwiki part.
     */
    private static final String[] ESCAPES_EXTRA = new String[] {
        LinkParser.SEPARATOR_QUERYSTRING,
        LinkParser.SEPARATOR_INTERWIKI,
        LinkParser.SEPARATOR_ANCHOR,
        "" + LinkParser.ESCAPE_CHAR};

    /**
     * @param link the link for which to generate a string representation
     * @return the string representation using the format:
     *         {@code (reference)[#anchor][?queryString][@interwikialias]}.
     */
    public String renderLinkReference(Link link)
    {
        StringBuilder buffer = new StringBuilder();

        if (link.getReference() != null) {
            // Make sure we escape special chars: #, @ and ? as they have special meaning in links, but only for
            // links to documents. Also escape \ since it's the escape char.
            String normalizedReference = link.getReference();
            if (link.getType() != LinkType.URI) {
                normalizedReference = addEscapesToReferencePart(link.getReference());
            }
            buffer.append(normalizedReference);
        }

        // Query string, anchor and interwiki parts only make sense for non URI links.
        if (link.getType() != LinkType.URI) {
            if (link.getAnchor() != null) {
                buffer.append('#');
                buffer.append(addEscapesToExtraParts(link.getAnchor()));
            }
            if (link.getQueryString() != null) {
                buffer.append('?');
                buffer.append(addEscapesToExtraParts(link.getQueryString()));
            }
            if (link.getInterWikiAlias() != null) {
                buffer.append('@');
                buffer.append(addEscapesToExtraParts(link.getInterWikiAlias()));
            }
        }

        return buffer.toString();
    }

    /**
     * @param text the reference to which to add escapes to
     * @return the modified text
     */
    private String addEscapesToReferencePart(String text)
    {
        return StringUtils.replaceEach(text, ESCAPES_REFERENCE, ESCAPE_REPLACEMENTS_REFERENCE);
    }

    /**
     * @param text the query string, anchor or interwiki parts to which to add escapes to
     * @return the modified text
     */
    private String addEscapesToExtraParts(String text)
    {
        return StringUtils.replaceEach(text, ESCAPES_EXTRA, ESCAPE_REPLACEMENTS_EXTRA);
    }

}
