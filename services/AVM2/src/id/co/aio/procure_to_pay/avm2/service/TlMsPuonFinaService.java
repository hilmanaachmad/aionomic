/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

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

import id.co.aio.procure_to_pay.avm2.TlMsPuonFina;

/**
 * Service object for domain model class {@link TlMsPuonFina}.
 */
public interface TlMsPuonFinaService {

    /**
     * Creates a new TlMsPuonFina. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsPuonFina if any.
     *
     * @param tlMsPuonFina Details of the TlMsPuonFina to be created; value cannot be null.
     * @return The newly created TlMsPuonFina.
     */
    TlMsPuonFina create(@Valid TlMsPuonFina tlMsPuonFina);


	/**
     * Returns TlMsPuonFina by given id if exists.
     *
     * @param tlmspuonfinaId The id of the TlMsPuonFina to get; value cannot be null.
     * @return TlMsPuonFina associated with the given tlmspuonfinaId.
	 * @throws EntityNotFoundException If no TlMsPuonFina is found.
     */
    TlMsPuonFina getById(Double tlmspuonfinaId);

    /**
     * Find and return the TlMsPuonFina by given id if exists, returns null otherwise.
     *
     * @param tlmspuonfinaId The id of the TlMsPuonFina to get; value cannot be null.
     * @return TlMsPuonFina associated with the given tlmspuonfinaId.
     */
    TlMsPuonFina findById(Double tlmspuonfinaId);

	/**
     * Find and return the list of TlMsPuonFinas by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tlmspuonfinaIds The id's of the TlMsPuonFina to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TlMsPuonFinas associated with the given tlmspuonfinaIds.
     */
    List<TlMsPuonFina> findByMultipleIds(List<Double> tlmspuonfinaIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TlMsPuonFina. It replaces all fields of the existing TlMsPuonFina with the given tlMsPuonFina.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsPuonFina if any.
     *
     * @param tlMsPuonFina The details of the TlMsPuonFina to be updated; value cannot be null.
     * @return The updated TlMsPuonFina.
     * @throws EntityNotFoundException if no TlMsPuonFina is found with given input.
     */
    TlMsPuonFina update(@Valid TlMsPuonFina tlMsPuonFina);


    /**
     * Partially updates the details of an existing TlMsPuonFina. It updates only the
     * fields of the existing TlMsPuonFina which are passed in the tlMsPuonFinaPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsPuonFina if any.
     *
     * @param tlmspuonfinaId The id of the TlMsPuonFina to be deleted; value cannot be null.
     * @param tlMsPuonFinaPatch The partial data of TlMsPuonFina which is supposed to be updated; value cannot be null.
     * @return The updated TlMsPuonFina.
     * @throws EntityNotFoundException if no TlMsPuonFina is found with given input.
     */
    TlMsPuonFina partialUpdate(Double tlmspuonfinaId, Map<String, Object> tlMsPuonFinaPatch);

    /**
     * Deletes an existing TlMsPuonFina with the given id.
     *
     * @param tlmspuonfinaId The id of the TlMsPuonFina to be deleted; value cannot be null.
     * @return The deleted TlMsPuonFina.
     * @throws EntityNotFoundException if no TlMsPuonFina found with the given id.
     */
    TlMsPuonFina delete(Double tlmspuonfinaId);

    /**
     * Deletes an existing TlMsPuonFina with the given object.
     *
     * @param tlMsPuonFina The instance of the TlMsPuonFina to be deleted; value cannot be null.
     */
    void delete(TlMsPuonFina tlMsPuonFina);

    /**
     * Find all TlMsPuonFinas matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlMsPuonFinas.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TlMsPuonFina> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TlMsPuonFinas matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlMsPuonFinas.
     *
     * @see Pageable
     * @see Page
     */
    Page<TlMsPuonFina> findAll(String query, Pageable pageable);

    /**
     * Exports all TlMsPuonFinas matching the given input query to the given exportType format.
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
     * Exports all TlMsPuonFinas matching the given input query to the given exportType format.
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
     * Retrieve the count of the TlMsPuonFinas in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TlMsPuonFina.
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