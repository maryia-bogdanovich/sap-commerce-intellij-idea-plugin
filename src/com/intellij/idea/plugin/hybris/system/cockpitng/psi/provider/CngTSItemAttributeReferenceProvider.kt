/*
 * This file is part of "SAP Commerce Developers Toolset" plugin for Intellij IDEA.
 * Copyright (C) 2019-2023 EPAM Systems <hybrisideaplugin@epam.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.intellij.idea.plugin.hybris.system.cockpitng.psi.provider

import com.intellij.idea.plugin.hybris.system.cockpitng.model.listView.ListColumn
import com.intellij.idea.plugin.hybris.system.cockpitng.psi.reference.CngTSItemAttributeReference
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.util.parentOfType
import com.intellij.psi.xml.XmlTag
import com.intellij.util.ProcessingContext
import com.intellij.util.xml.DomManager

@Service
class CngTSItemAttributeReferenceProvider : PsiReferenceProvider() {

    override fun getReferencesByElement(
        element: PsiElement, context: ProcessingContext
    ) = element.parentOfType<XmlTag>()
        ?.let { DomManager.getDomManager(element.project).getDomElement(it) }
        ?.let { it as? ListColumn }
        ?.springBean
        ?.takeUnless { it.stringValue == null }
        ?.let { emptyArray() }
        ?: arrayOf(CngTSItemAttributeReference(element))

    companion object {
        val instance: PsiReferenceProvider = ApplicationManager.getApplication().getService(CngTSItemAttributeReferenceProvider::class.java)
    }
}