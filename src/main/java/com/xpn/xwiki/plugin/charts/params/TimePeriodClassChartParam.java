/*
 * Copyright 2006, XpertNet SARL, and individual contributors as indicated
 * by the contributors.txt.
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
 *
 * @author sdumitriu
 */
package com.xpn.xwiki.plugin.charts.params;

import org.jfree.data.time.*;

public class TimePeriodClassChartParam extends ChoiceChartParam {

	public TimePeriodClassChartParam(String name) {
		super(name);
	}

	public TimePeriodClassChartParam(String name, boolean isOptional) {
		super(name, isOptional);
	}

	protected void init() {
		addChoice("year", Year.class);
		addChoice("quarter", Quarter.class);
		addChoice("month", Month.class);
		addChoice("week", Week.class);
		addChoice("day", Day.class);
		addChoice("hour", Hour.class);
		addChoice("minute", Minute.class);
		addChoice("second", Second.class);
		addChoice("millisecond", Millisecond.class);
	}

	public Class getType() {
		return Class.class;
	}
}
