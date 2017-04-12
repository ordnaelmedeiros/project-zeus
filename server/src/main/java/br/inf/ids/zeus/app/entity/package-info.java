@XmlJavaTypeAdapters({
	@XmlJavaTypeAdapter(type=LocalDate.class,  value=LocalDateAdapter.class),
})
package br.inf.ids.zeus.app.entity;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import br.inf.ids.zeus.core.adapters.LocalDateAdapter;

