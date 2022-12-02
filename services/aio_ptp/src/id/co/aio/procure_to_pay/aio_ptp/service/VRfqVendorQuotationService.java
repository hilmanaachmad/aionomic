/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.VRfqVendorQuotation;

/**
 * Service object for domain model class {@link VRfqVendorQuotation}.
 */
public interface VRfqVendorQuotationService {

    /**
     * Creates a new VRfqVendorQuotation. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VRfqVendorQuotation if any.
     *
     * @param vrfqVendorQuotation Details of the VRfqVendorQuotation to be created; value cannot be null.
     * @return The newly created VRfqVendorQuotation.
     */
    VRfqVendorQuotation create(@Valid VRfqVendorQuotation vrfqVendorQuotation);


	/**
     * Returns VRfqVendorQuotation by given id if exists.
     *
     * @param vrfqvendorquotationId The id of the VRfqVendorQuotation to get; value cannot be null.
     * @return VRfqVendorQuotation associated with the given vrfqvendorquotationId.
	 * @throws EntityNotFoundException If no VRfqVendorQuotation is found.
     */
    VRfqVendorQuotation getById(Integer vrfqvendorquotationId);

    /**
     * Find and return the VRfqVendorQuotation by given id if exists, returns null otherwise.
     *
     * @param vrfqvendorquotationId The id of the VRfqVendorQuotation to get; value cannot be null.
     * @return VRfqVendorQuotation associated with the given vrfqvendorquotationId.
     */
    VRfqVendorQuotation findById(Integer vrfqvendorquotationId);

	/**
     * Find and return the list of VRfqVendorQuotations by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vrfqvendorquotationIds The id's of the VRfqVendorQuotation to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VRfqVendorQuotations associated with the given vrfqvendorquotationIds.
     */
    List<VRfqVendorQuotation> findByMultipleIds(List<Integer> vrfqvendorquotationIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VRfqVendorQuotation. It replaces all fields of the existing VRfqVendorQuotation with the given vrfqVendorQuotation.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VRfqVendorQuotation if any.
     *
     * @param vrfqVendorQuotation The details of the VRfqVendorQuotation to be updated; value cannot be null.
     * @return The updated VRfqVendorQuotation.
     * @throws EntityNotFoundException if no VRfqVendorQuotation is found with given input.
     */
    VRfqVendorQuotation update(@Valid VRfqVendorQuotation vrfqVendorQuotation);


    /**
     * Partially updates the details of an existing VRfqVendorQuotation. It updates only the
     * fields of the existing VRfqVendorQuotation which are passed in the vrfqVendorQuotationPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VRfqVendorQuotation if any.
     *
     * @param vrfqvendorquotationId The id of the VRfqVendorQuotation to be deleted; value cannot be null.
     * @param vrfqVendorQuotationPatch The partial data of VRfqVendorQuotation which is supposed to be updated; value cannot be null.
     * @return The updated VRfqVendorQuotation.
     * @throws EntityNotFoundException if no VRfqVendorQuotation is found with given input.
     */
    VRfqVendorQuotation partialUpdate(Integer vrfqvendorquotationId, Map<String, Object> vrfqVendorQuotationPatch);

    /**
     * Deletes an existing VRfqVendorQuotation with the given id.
     *
     * @param vrfqvendorquotationId The id of the VRfqVendorQuotation to be deleted; value cannot be null.
     * @return The deleted VRfqVendorQuotation.
     * @throws EntityNotFoundException if no VRfqVendorQuotation found with the given id.
     */
    VRfqVendorQuotation delete(Integer vrfqvendorquotationId);

    /**
     * Deletes an existing VRfqVendorQuotation with the given object.
     *
     * @param vrfqVendorQuotation The instance of the VRfqVendorQuotation to be deleted; value cannot be null.
     */
    void delete(VRfqVendorQuotation vrfqVendorQuotation);

    /**
     * Find all VRfqVendorQuotations matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VRfqVendorQuotations.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VRfqVendorQuotation> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VRfqVendorQuotations matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VRfqVendorQuotations.
     *
     * @see Pageable
     * @see Page
     */
    Page<VRfqVendorQuotation> findAll(String query, Pageable pageable);

    /**
     * Exports all VRfqVendorQuotations matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all VRfqVendorQuotations matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the VRfqVendorQuotations in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VRfqVendorQuotation.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}