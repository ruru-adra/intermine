package org.flymine.web.results;

/*
 * Copyright (C) 2002-2003 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.actions.TilesAction;
import org.apache.struts.tiles.ComponentContext;

import org.flymine.objectstore.query.Results;
import org.flymine.web.Constants;

/**
 * Implementation of <strong>TilesAction</strong>. Assembles data for
 * a results view.
 *
 * @author Andrew Varley
 */
public class ResultsViewController extends TilesAction
{
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param context The Tiles ComponentContext
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return an ActionForward object defining where control goes next
     *
     * @exception ServletException if a servlet error occurs
     */
    public ActionForward execute(ComponentContext context, ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession();

        Results results = (Results) session.getAttribute("results");

        DisplayableResults dr = null;
        if (results != null) {
            dr = new DisplayableResults(results);
            session.removeAttribute("results");
        } else {
            DisplayableResults drOrig = (DisplayableResults)
                session.getAttribute(Constants.RESULTS_TABLE);
            dr = new DisplayableResults(drOrig.getResults());
            dr.update(drOrig);
        }
        session.setAttribute(Constants.RESULTS_TABLE, dr);

        return null;
    }
}
